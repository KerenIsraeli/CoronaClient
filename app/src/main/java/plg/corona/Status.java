package plg.corona;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Class represents the status of sick, healed and deceased people from corona.
 * Created by keren on 01/04/2020.
 */
public class Status implements Parcelable {

    // region Constants

    private static final int INITIAL_NUM_IN_GROUP = 0;

    // endregion

    // region Members

    private Random _rnd = new Random();

    private int _sick;
    private int _healed;
    private int _deceased;

    // endregion

    // region Constructors

    private Status(int sick, int healed, int deceased) {
        _sick = sick;
        _healed = healed;
        _deceased = deceased;

    }

    public Status() {
        this(INITIAL_NUM_IN_GROUP, INITIAL_NUM_IN_GROUP, INITIAL_NUM_IN_GROUP);
    }

    protected Status(Parcel in) {
        this(in.readInt(), in.readInt(), in.readInt());
    }

    // endregion

    // region Properties

    public int getSick() {
        return _sick;
    }

    public void setSick(int sick) {
        _sick = sick;
    }

    public int getHealed() {
        return _healed;
    }

    public void setHealed(int healed) {
        _healed = healed;
    }

    public int getDeceased() {
        return _deceased;
    }

    public void setDeceased(int deceased) {
        _deceased = deceased;
    }

    // endregion

    // region Public Methods

    /**
     * Method removes sick people (healed or deceased).
     * @param sick number of sick people who are removed.
     */
    public void removeSick(int sick) {
        _sick -= sick;
    }

    /**
     * Method adds new sick people.
     * @param newSick number of new sick people.
     */
    public void addSick(int newSick) {
        _sick += newSick;
    }

    /**
     * Method adds new healed people.
     * @param newHealed number of new healed people.
     */
    public void addHealed(int newHealed) {
        _healed += newHealed;
    }

    /**
     * Method adds new deceased people.
     * @param newDeceased number of new deceased people.
     */
    public void addDeceased(int newDeceased) {
        _deceased += newDeceased;
    }

    /**
     * Method creates a string representing status.
     * @return representation of status.
     */
    public String toString(){
        return "Sick: " + _sick + "\nDeceased: " + _deceased + "\nHealed: " + _healed;
    }

    // endregion

    // region Parcelable

    public static final Parcelable.Creator<Status> CREATOR =
            new Parcelable.Creator<Status>() {
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_sick);
        dest.writeInt(_healed);
        dest.writeInt(_deceased);
    }

    // endregion

}
