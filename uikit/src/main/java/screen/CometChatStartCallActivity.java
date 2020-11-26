package screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CallSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.R;
import com.google.android.material.snackbar.Snackbar;

import constant.StringContract;

/**
 * CometChatStartCallActivity is activity class which is used to start a call. It takes sessionID
 * as a parameter and start call for particular sessionID.
 *
 * Created On - 22nd August 2020
 *
 * Modified On -  07th October 2020
 *
 */
public class CometChatStartCallActivity extends AppCompatActivity {

    public static CometChatStartCallActivity activity;

    private RelativeLayout mainView;

    private String sessionID;

    private String type;

    private CallSettings callSettings;

    private boolean joinOnGoing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_comet_chat_start_call);
        mainView = findViewById(R.id.call_view);
        sessionID = getIntent().getStringExtra(StringContract.IntentStrings.SESSION_ID);
        type = getIntent().getStringExtra(StringContract.IntentStrings.TYPE);
        if (type!=null && type.equalsIgnoreCase(CometChatConstants.RECEIVER_TYPE_USER))
            callSettings = new CallSettings.CallSettingsBuilder(this,mainView)
                    .setSessionId(sessionID)
                    .setMode(CallSettings.MODE_SINGLE)
                    .build();
        else
            callSettings = new CallSettings.CallSettingsBuilder(this,mainView)
                    .setSessionId(sessionID)
                    .build();

        CometChat.startCall(callSettings, new CometChat.OngoingCallListener() {
                @Override
                public void onUserJoined(User user) {
                    Log.e("onUserJoined: ", user.getUid());
                }

                @Override
                public void onUserLeft(User user) {
                    Snackbar.make(mainView, "User Left: " + user.getName(), Snackbar.LENGTH_LONG).show();
                    Log.e("onUserLeft: ", user.getUid());
                }

                @Override
                public void onError(CometChatException e) {
                    Log.e("onstartcallError: ", e.getMessage());
                    Toast.makeText(CometChatStartCallActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCallEnded(Call call) {
                    Log.e("TAG", "onCallEnded: ");
                    finish();
                }
        });
    }
}