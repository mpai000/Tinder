package com.example.dogfinder;

public class DogData {
    private String dogID;
    private String dogLocation;
    private String dogBreed;
    private String dogMaturity;
    private String dogGender;
    private String dogSize;
    private String dogName;
    private String dogPictureLink;


    public DogData(String dogID, String dogLocation, String dogBreed, String dogMaturity, String dogGender , String dogSize, String dogName, String dogPictureLink) {
        this.dogID = dogID;
        this.dogLocation = dogLocation;
        this.dogBreed = dogBreed;
        this.dogMaturity = dogMaturity;
        this.dogGender = dogGender;
        this.dogSize = dogSize;
        this.dogName = dogName;
        this.dogPictureLink = dogPictureLink;
    }

    public String getDogID() {
        return dogID;
    }

    public String getDogLocation() {
        return dogLocation;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public String getDogMaturity() {
        return dogMaturity;
    }

    public String getDogGender() {
        return dogGender;
    }

    public String getDogSize() {
        return dogSize;
    }

    public String getDogName() {
        return dogName;
    }

    public String getDogPictureLink() {
        return dogPictureLink;
    }
}
