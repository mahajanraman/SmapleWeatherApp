package weatherapp.test.com.receivers;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import weatherapp.test.com.utils.ParseException;

/**
 * Created by raman on 7/23/2015.
 * Used to set the receiver and pass the result to the respective class whose registers for the receiver
 */
public class ProcessResultReceiver  extends ResultReceiver{

    private Receiver mReceiver;

    public ProcessResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData) throws ParseException;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            try {
                mReceiver.onReceiveResult(resultCode, resultData);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
