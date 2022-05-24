package com.example.galleryapp.Model;



public class ImagesModel {

    private String id, name,imagePath,size,description;


    public ImagesModel(String id, String name, String imagePath, String size, String description) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.size = size;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
