package ca.lambton.allan.xlambton;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import ca.lambton.allan.xlambton.database.model.Agent;

public class AgentFormHelper {

    private final EditText mUsername;
    private final EditText mPassword;
    private final EditText mName;
    private final EditText mLevel;
    private final EditText mAgency;
    private final EditText mWebSite;
    private final EditText mCountry;
    private final EditText mPhone;
    private final EditText mAddress;
    private final ImageView mPhoto;

    private final Resources resources;

    private Agent agent;

    public AgentFormHelper(AgentFormActivity activity) {
        mUsername = activity.findViewById(R.id.username);
        mPassword = activity.findViewById(R.id.password);
        mName = activity.findViewById(R.id.name);
        mLevel = activity.findViewById(R.id.level);
        mAgency = activity.findViewById(R.id.agency);
        mWebSite = activity.findViewById(R.id.website);
        mCountry = activity.findViewById(R.id.country);
        mPhone = activity.findViewById(R.id.phone);
        mAddress = activity.findViewById(R.id.address);
        mPhoto = activity.findViewById(R.id.form_photo);

        resources = activity.getResources();

        agent = new Agent();
    }

    public Agent getAgent() {
        agent.setUsername(mUsername.getText().toString());
        agent.setPassword(mPassword.getText().toString());
        agent.setName(mName.getText().toString());
        agent.setLevel(mLevel.getText().toString());
        agent.setAgency(mAgency.getText().toString());
        agent.setWebSite(mWebSite.getText().toString());
        agent.setCountry(mCountry.getText().toString());
        agent.setPhone(mPhone.getText().toString());
        agent.setAddress(mAddress.getText().toString());
        agent.setPhoto((String) mPhoto.getTag());
        return agent;
    }

    public void fillForm(Agent agent) {
        mUsername.setText(agent.getUsername());
        mPassword.setText(agent.getPassword());
        mUsername.setEnabled(false);
        mPassword.setEnabled(false);

        mName.setText(agent.getName());
        mLevel.setText(agent.getLevel());
        mAgency.setText(agent.getAgency());
        mWebSite.setText(agent.getWebSite());
        mCountry.setText(agent.getCountry());
        mPhone.setText(agent.getPhone());
        mAddress.setText(agent.getAddress());

        loadImage(agent.getPhoto());

        this.agent = agent;
    }

    public void loadImage(String photoPath) {
        if (photoPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            Bitmap lowDefBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            mPhoto.setImageBitmap(lowDefBitmap);
            mPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            mPhoto.setTag(photoPath);
        }
    }

    public boolean valid() {
        if (TextUtils.isEmpty(mUsername.getText().toString())) {
            mUsername.setError(resources.getString(R.string.error_field_required));
            mUsername.requestFocus();
            return false;
        } else {
            mUsername.setError(null);
        }

        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            mPassword.setError(resources.getString(R.string.error_field_required));
            mPassword.requestFocus();
            return false;
        } else {
            mPassword.setError(null);
        }

        if (TextUtils.isEmpty(mName.getText().toString())) {
            mName.setError(resources.getString(R.string.error_field_required));
            mName.requestFocus();
            return false;
        } else {
            mName.setError(null);
        }

        if (TextUtils.isEmpty(mLevel.getText().toString())) {
            mLevel.setError(resources.getString(R.string.error_field_required));
            mLevel.requestFocus();
            return false;
        } else {
            mLevel.setError(null);
        }

        if (TextUtils.isEmpty(mPhone.getText().toString())) {
            mPhone.setError(resources.getString(R.string.error_field_required));
            mPhone.requestFocus();
            return false;
        } else {
            mPhone.setError(null);
        }
        return true;
    }
}
