package abi.parveen.donate;

public class DataClass {
    public  String adataName;
    public  String bdataLocation;
    public String cdataSpinner;
    public String ddataGmail;
    public String edataImage;

    public String getAdataName() {
        return adataName;
    }

    public String getBdataLocation() {
        return bdataLocation;
    }

    public String getCdataSpinner() {
        return cdataSpinner;
    }

    public String getDdataGmail() {
        return ddataGmail;
    }

    public String getEdataImage() {
        return edataImage;
    }

    public DataClass(String adataName, String bdataLocation, String cdataSpinner, String ddataGmail, String edataImage) {
        this.adataName = adataName;
        this.bdataLocation = bdataLocation;
        this.cdataSpinner = cdataSpinner;
        this.ddataGmail = ddataGmail;
        this.edataImage = edataImage;
    }

    public DataClass(){

    }
}
