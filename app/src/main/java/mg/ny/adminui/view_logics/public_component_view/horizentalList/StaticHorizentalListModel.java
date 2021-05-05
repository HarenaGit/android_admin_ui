package mg.ny.adminui.view_logics.public_component_view.horizentalList;

import android.os.Parcel;
import android.os.Parcelable;

import mg.ny.adminui.data_model.PlaneDataModel;

public class StaticHorizentalListModel implements Parcelable {
    private String text;
    public StaticHorizentalListModel(String text){
        this.text = text;
    }
    public String getText(){
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
    }
    public StaticHorizentalListModel(Parcel source){
        text = source.readString();
    }
    public static final Creator<StaticHorizentalListModel> CREATOR = new Creator<StaticHorizentalListModel>() {
        @Override
        public StaticHorizentalListModel[] newArray(int size) {
            return new StaticHorizentalListModel[size];
        }
        @Override
        public StaticHorizentalListModel createFromParcel(Parcel source) {
            return new StaticHorizentalListModel(source);
        }

    };
}

