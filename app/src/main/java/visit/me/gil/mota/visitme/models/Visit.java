package visit.me.gil.mota.visitme.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Predicate;

import visit.me.gil.mota.visitme.utils.Functions;

/**
 * Created by mota on 16/4/2018.
 */
public class Visit implements Parcelable {

    private static final String[] days = {"Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom"};

    private String _id;
    private String kind;
    private String dayOfVisit;
    private User guest;
    private User resident;
    private int companions;
    private Community community;
    private String partOfDay;
    private String limit;
    private String token;
    private Interval[] intervals;

    protected Visit(Parcel in) {
        _id = in.readString();
        kind = in.readString();
        dayOfVisit = in.readString();
        partOfDay = in.readString();
        companions = in.readInt();
        guest = in.readParcelable(User.class.getClassLoader());
        resident = in.readParcelable(User.class.getClassLoader());
        community = in.readParcelable(Community.class.getClassLoader());
        intervals = in.createTypedArray(Interval.CREATOR);
        limit = in.readString();
        token = in.readString();
    }

    public static final Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getLimit() {
        return limit;
    }

    public String getToken() {
        return token.split("-")[1];
    }

    public String getDayOfVisit() {
        if (kind.equals("SPORADIC"))
            return "";
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return kind.equals("SCHEDULED") ? showAsScheduled() : showAsFrequent();
    }

    private String parseDate(String date) {
        DateFormat formated = SimpleDateFormat.getDateInstance();
        formated.setTimeZone(TimeZone.getDefault());
        try {
            return formated.format(new Date(date.substring(0, 10).replace("-", "/")));
        } catch (Exception e) {
            return formated.format(new Date(date));
        }
    }

    private String showAsScheduled() {
        return parseDate(dayOfVisit) + " " + getDayPartString();
    }

    private String showAsFrequent() {
        StringBuilder str = new StringBuilder();
        for (Interval i : intervals) {
            str.append(days[i.getDay()]).append(" ");
        }
        return str.toString();
    }

    public void setDayOfVisit(String dayOfVisit) {
        this.dayOfVisit = dayOfVisit;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Interval[] getIntervals() {
        return intervals;
    }

    public void setIntervals(Interval[] intervals) {
        this.intervals = intervals;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "_id='" + _id + '\'' +
                ", kind='" + kind + '\'' +
                ", dayOfVisit=" + dayOfVisit +
                ", guest=" + guest +
                ", community=" + community +
                ", intervals=" + Arrays.toString(intervals) +
                '}';
    }

    public String getKindString() {
        switch (kind) {
            case "SPORADIC":
                return "Esporadica";
            case "FREQUENT":
                return "Frecuente";
            case "SCHEDULED":
                return "Esperada";
        }
        return "";
    }

    public User getResident() {
        return resident;
    }

    public void setResident(User resident) {
        this.resident = resident;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(kind);
        parcel.writeString(dayOfVisit);
        parcel.writeString(partOfDay);
        parcel.writeInt(companions);
        parcel.writeParcelable(guest, i);
        parcel.writeParcelable(resident, i);
        parcel.writeParcelable(community, i);
        parcel.writeTypedArray(intervals, i);
        parcel.writeString(limit);
        parcel.writeString(token);
    }

    public String getDayPartString() {

        switch (partOfDay) {
            case "AFTERNOON":
                return "Tarde";
            case "MORNING":
                return "Mañana";
            case "NIGHT":
                return "Noche";
            case "ALL DAY":
                return "Todo El dia";
            default:
                return "Mañana";
        }
    }

    public Date getDayOfVisitAsDate() {
        return new Date(dayOfVisit.substring(0, 10).replace("-", "/"));
    }

    public int getCompanions() {
        return companions;
    }

    public void setCompanions(int companions) {
        this.companions = companions;
    }

    public void setPartOfDay(String partOfDay) {
        this.partOfDay = partOfDay;
    }

    public void setDayOfVisit(Date dayAsDate) {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        this.dayOfVisit = dt.format(dayAsDate);
    }

    public String getValidTill() {
        return kind.equals("SCHEDULED") ? parseDate(dayOfVisit) : parseDate(limit);
    }
}
