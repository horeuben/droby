package com.example.reube.droby.Database;

import java.util.ArrayList;

/**
 * Created by reube on 27/7/2017.
 */

public class ClothesTags {
    private int clothes_id;
    private String colour,fit,cut,weather,specific_cut,prints,embellishments,material,formality,colour_scheme,style;

    public ArrayList<String> getTags(){
        ArrayList<String> tags = new ArrayList<>();
        tags.add(colour);
        tags.add(fit);
        tags.add(cut);
        tags.add(weather);
        tags.add(specific_cut);
        tags.add(prints);
        tags.add(embellishments);
        tags.add(material);
        tags.add(formality);
        tags.add(colour_scheme);
        tags.add(style);

        return tags;
    }

    public int getClothes_id() {
        return clothes_id;
    }

    public void setClothes_id(int clothes_id) {
        this.clothes_id = clothes_id;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getSpecific_cut() {
        return specific_cut;
    }

    public void setSpecific_cut(String specific_cut) {
        this.specific_cut = specific_cut;
    }

    public String getPrints() {
        return prints;
    }

    public void setPrints(String prints) {
        this.prints = prints;
    }

    public String getEmbellishments() {
        return embellishments;
    }

    public void setEmbellishments(String embellishments) {
        this.embellishments = embellishments;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getFormality() {
        return formality;
    }

    public void setFormality(String formality) {
        this.formality = formality;
    }

    public String getColour_scheme() {
        return colour_scheme;
    }

    public void setColour_scheme(String colour_scheme) {
        this.colour_scheme = colour_scheme;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
