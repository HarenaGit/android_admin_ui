package mg.ny.adminui;

public class PlaneDataModel {
    private String id;
    private String name;
    private String placeCount;
    public PlaneDataModel(String id, String name, String placeCount){
        this.id = id;
        this.name = name;
        this.placeCount = placeCount;
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

}
