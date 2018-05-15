package visit.me.gil.mota.visitme.models;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import visit.me.gil.mota.visitme.utils.Functions;

/**
 * Created by mota on 16/4/2018.
 */

public class Visit {
    private String _id;
    private String kind;
    private Date dayOfVisit;
    private User guest;
    private User resident;
    private Community community;
    private Interval[] intervals;

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

    public String getDayOfVisit() {
        if (kind.equals("SPORADIC"))
            return "";


        int day  = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return dayOfVisit != null ? new SimpleDateFormat("dd/MM/yyyy").format(dayOfVisit) : getNextInterval(day);
    }

    private String getNextInterval(int day) {
        List<Interval> intrvl = findIntervalsInDay(day);

        if(intrvl.isEmpty())
            return getNextInterval(day == 6 ? 0 : ++day);
        else
            return intrvl.size() == 1 ? intrvl.get(0).toString() : findNearIntervalWithHours(intrvl);

    }

    private String findNearIntervalWithHours(List<Interval> intrvl) {
        int hour  = Calendar.getInstance().get(Calendar.HOUR);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        Interval minimun = null;
        Log.i("VISIT","HOUR!"+ hour);
        int rest = 100;
        int op = 0;
        for (Interval i : intrvl)
        {
           op = i.getTo() - (hour * 100 + hour);
           if(op >= 0 && op <= rest)
           {
               rest = op;
               minimun = i;
           }

        }
        return minimun != null ? minimun.toString() : intrvl.get(0).toString();
    }

    private List<Interval> findIntervalsInDay(int day)
    {
        List<Interval> intrvls = new ArrayList<>();

        for (Interval i : intervals)
            if(i.getDay() == day)
                intrvls.add(i);

        return intrvls;
    }

    public void setDayOfVisit(Date dayOfVisit) {
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
        return kind;
    }

    public User getResident() {
        return resident;
    }

    public void setResident(User resident) {
        this.resident = resident;
    }
}
