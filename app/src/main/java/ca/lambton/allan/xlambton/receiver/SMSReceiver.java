package ca.lambton.allan.xlambton.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;
import android.widget.Toast;

import ca.lambton.allan.xlambton.R;
import ca.lambton.allan.xlambton.database.repository.AgentRepository;

public class SMSReceiver extends BroadcastReceiver {

    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] smsPdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] smsPdu = (byte[]) smsPdus[0];

        SmsMessage sms;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // If Android version M or newer:
            String smsFormat = (String) intent.getSerializableExtra("format");
            sms = SmsMessage.createFromPdu(smsPdu, smsFormat);
        } else {
            // If Android version L or older:
            sms = SmsMessage.createFromPdu(smsPdu);
        }

        String smsSender = sms.getDisplayOriginatingAddress();
        AgentRepository repository = new AgentRepository(context);
        if (repository.existPhoneNumber(smsSender)) {
            Toast.makeText(context, "Agent Message arrived !!!", Toast.LENGTH_LONG).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }
    }
}
