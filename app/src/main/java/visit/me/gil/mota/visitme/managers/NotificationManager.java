package visit.me.gil.mota.visitme.managers;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OSNotificationReceivedResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigInteger;

import visit.me.gil.mota.visitme.R;
import visit.me.gil.mota.visitme.models.Community;
import visit.me.gil.mota.visitme.models.User;
import visit.me.gil.mota.visitme.models.Visit;
import visit.me.gil.mota.visitme.services.NotificationExtender;
import visit.me.gil.mota.visitme.utils.Functions;
import visit.me.gil.mota.visitme.views.activities.UnexpectedVisitorActivity;

/**
 * Created by mota on 18/4/2018.
 */

public class NotificationManager implements OneSignal.NotificationOpenedHandler {
    private static NotificationManager instance;
    private Context context;
    private NotificationExtender extender;

    private NotificationManager() {

    }

    public void init(Context context) {
        this.context = context;
    }

    public static NotificationManager getInstance() {
        if (instance == null)
            instance = new NotificationManager();
        return instance;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        JSONObject data = result.notification.payload.additionalData;

    }

    public boolean onNewNotification(OSNotificationReceivedResult result) throws JSONException {
        JSONObject notification = result.payload.additionalData;
        String auth = UserManager.getInstance().getAuth();
        if (notification != null && !auth.isEmpty())
        {
            String type = notification.getString("type");
            if (type.equals("UNEXPECTED VISIT"))
                onUnexpectedVisitor(notification);
            else
                displayNotification(notification);
        }
        return true;
    }

    private void onUnexpectedVisitor(JSONObject notification)
    {
        Intent notifyIntent = new Intent(context, UnexpectedVisitorActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notifyIntent.putExtra("data",notification.toString());
        context.startActivity(notifyIntent);
    }

    public void addServiceInstance(NotificationExtender notificationExtender) {
        this.extender = notificationExtender;
    }

    private void displayNotification(JSONObject notificationData) throws JSONException {
        NotificationExtenderService.OverrideSettings overrideSettings = new NotificationExtenderService.OverrideSettings();
        overrideSettings.extender = buildByNotificationType(notificationData);
        extender.showNotification(overrideSettings);
    }

    private NotificationCompat.Extender buildByNotificationType(JSONObject notification) throws JSONException {
        String type = notification.getString("type");
        JSONObject data = notification.getJSONObject("data");
        switch (type)
        {
            case "ALERT":
                return buildAlert(data);
            case "VISIT_ARRIVE":
                return buildVisitArrive(data);
            case "INVITATION":
                return buildInvitation(data);
                default:
                    return buildAlert(data);
        }

    }

    private NotificationCompat.Extender buildInvitation(JSONObject data) throws JSONException {
        final Visit v = Functions.parse(data, Visit.class);
         return new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                return builder.setContentTitle("Tu visita ya ha sido programada")
                        .setContentText(v.getResident().getName())
                        .setContentInfo(v.getCommunity().getName())
                        .setSubText(v.getDayOfVisit())
                        .setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setSmallIcon(R.drawable.hashtag);
            }
        };
    }

    private NotificationCompat.Extender buildVisitArrive(JSONObject data) throws JSONException {
        final Visit v = Functions.parse(data, Visit.class);
        return new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                return builder.setContentTitle("Tu Visita ha llegado")
                        .setContentText(v.getGuest().getName())
                        .setContentInfo(v.getCommunity().getName())
                        .setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setSmallIcon(R.drawable.hashtag);
            }
        };
    }

    private NotificationCompat.Extender buildAlert(JSONObject data) throws JSONException {
        final String message = data.getString("message");
        return new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                return builder.setContentTitle("¡ALERTA!")
                        .setContentText(message)
                        .setColor(context.getResources().getColor(R.color.error))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSmallIcon(R.drawable.hashtag);
            }
        };
    }
}
