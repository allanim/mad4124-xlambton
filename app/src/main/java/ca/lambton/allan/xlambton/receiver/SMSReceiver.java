package ca.lambton.allan.xlambton.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

import ca.lambton.allan.xlambton.R;
import ca.lambton.allan.xlambton.database.model.Mission;
import ca.lambton.allan.xlambton.database.repository.AgentRepository;
import ca.lambton.allan.xlambton.database.repository.MissionRepository;
import ca.lambton.allan.xlambton.utils.SharedPreferencesUtils;

public class SMSReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (Objects.requireNonNull(intent.getAction()).equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            StringBuilder smsBody = new StringBuilder();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.getDisplayOriginatingAddress();
                    smsBody.append(smsMessage.getMessageBody());
                }
            } else {
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        Log.e(this.getClass().getSimpleName(), "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody.append(messages[i].getMessageBody());
                    }
                    smsSender = messages[0].getOriginatingAddress();
                }
            }

            AgentRepository repository = new AgentRepository(context);
            if (repository.existPhoneNumber(smsSender)) {
                Log.d("SMSReceiver", "This is TODO");

                if (hasId(context)) {
                    saveMission(context, smsBody.toString());
                }

                Toast.makeText(context, "Mission SMS: " + smsBody.toString(),
                        Toast.LENGTH_LONG).show();

                MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
                mp.start();
            }

            Log.d("SMSReceiver", "SMS detected: From " + smsSender + " With text " + smsBody);
        }
    }

    private boolean hasId(Context context) {
        return SharedPreferencesUtils.instance(context).contains("MID");
    }

    private void saveMission(Context context, String smsBody) {
        // add mission
        Mission mission = new Mission();
        mission.setAgentId(SharedPreferencesUtils.instance(context).getInt("MID"));
        mission.setDescription(smsBody);
        mission.setStatus("TODO");

        MissionRepository repository = new MissionRepository(context);
        repository.insert(mission);

//        Toast.makeText(context, "Add Mission: " + smsBody, Toast.LENGTH_SHORT).show();
    }

}
