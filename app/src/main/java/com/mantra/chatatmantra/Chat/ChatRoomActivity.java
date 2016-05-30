package com.mantra.chatatmantra.Chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mantra.chatatmantra.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewsById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by rajat on 28/05/16.
 */
@EActivity(R.layout.chat_layout)
public class ChatRoomActivity extends AppCompatActivity {


    private String chatRoomId;
    @ViewsById(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<Message> messageArrayList;

    @ViewsById(R.id.message)
    private EditText inputMessage;

    @ViewsById(R.id.btn_send)
    private Button btnSend;

    @ViewsById(R.id.chat_group_name)
    TextView chatGroupName;

    @ViewsById(R.id.user_thumbview)
    ImageView userThumbView;

    @ViewsById(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @AfterViews
    public void init(){
        Intent intent = getIntent();
        chatRoomId = intent.getStringExtra("chat_room_id");
        String title = intent.getStringExtra("name");
        chatGroupName.setText(title);

        if (chatRoomId == null) {
            Toast.makeText(getApplicationContext(), "Chat room not found!", Toast.LENGTH_SHORT).show();
            finish();
        }


    }
    /**
     * Posting a new message in chat room
     * will make an http call to our server. Our server again sends the message
     * to all the devices as push notification
     * */
    @Click(R.id.btn_send)
    private void sendMessage() {
        final String message = this.inputMessage.getText().toString().trim();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getApplicationContext(), "Enter a message", Toast.LENGTH_SHORT).show();
            return;
        }
//
//        String endPoint = EndPoints.CHAT_ROOM_MESSAGE.replace("_ID_", chatRoomId);
//
//        Log.e(TAG, "endpoint: " + endPoint);
//
        this.inputMessage.setText("");
//
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                endPoint, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.e(TAG, "response: " + response);
//
//                try {
//                    JSONObject obj = new JSONObject(response);
//
//                    // check for error
//                    if (obj.getBoolean("error") == false) {
//                        JSONObject commentObj = obj.getJSONObject("message");
//
//                        String commentId = commentObj.getString("message_id");
//                        String commentText = commentObj.getString("message");
//                        String createdAt = commentObj.getString("created_at");
//
//                        JSONObject userObj = obj.getJSONObject("user");
//                        String userId = userObj.getString("user_id");
//                        String userName = userObj.getString("name");
//                        User user = new User(userId, userName, null);
//
//                        Message message = new Message();
//                        message.setId(commentId);
//                        message.setMessage(commentText);
//                        message.setCreatedAt(createdAt);
//                        message.setUser(user);
//
//                        messageArrayList.add(message);
//
//                        mAdapter.notifyDataSetChanged();
//                        if (mAdapter.getItemCount() > 1) {
//                            // scrolling to bottom of the recycler view
//                            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, mAdapter.getItemCount() - 1);
//                        }
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "" + obj.getString("message"), Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (JSONException e) {
//                    Log.e(TAG, "json parsing error: " + e.getMessage());
//                    Toast.makeText(getApplicationContext(), "json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkResponse networkResponse = error.networkResponse;
//                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
//                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                inputMessage.setText(message);
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("user_id", MyApplication.getInstance().getPrefManager().getUser().getId());
//                params.put("message", message);
//
//                Log.e(TAG, "Params: " + params.toString());
//
//                return params;
//            };
//        };
//
//
//        // disabling retry policy so that it won't make
//        // multiple http calls
//        int socketTimeout = 0;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//
//        strReq.setRetryPolicy(policy);
//
//        //Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(strReq);
    }


    @Subscribe
    public void RecieveMessage(Message message){
        messageArrayList.add(message);
    }
}
