package mg.ny.adminui;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaneDataModel implements Parcelable {
    private String id;
    private String name;
    private String placeCount;
    public PlaneDataModel(String id, String name, String placeCount){
        this.id = id;
        this.name = name;
        this.placeCount = placeCount;
    }
    public PlaneDataModel(Parcel source){
        id = source.readString();
        name = source.readString();
        placeCount = source.readString();
    }
    public String getId(){
        return  this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getPlaceCount(){
        return this.placeCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(placeCount);
    }

    public static final Creator<PlaneDataModel> CREATOR = new Creator<PlaneDataModel>() {
        @Override
        public PlaneDataModel[] newArray(int size) {
            return new PlaneDataModel[size];
        }
        @Override
        public PlaneDataModel createFromParcel(Parcel source) {
            return new PlaneDataModel(source);
        }

    };
}
