/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Matt
 */
public class Address {
    private String addressId;
    private String address;
    private String address2;
    private String cityId; 
    private String city;
    private String country;
    private String postalCode;    
    private String phone;
    
    
    public void Address(String addressId,String address, String address2, String cityId, String city ,String country,String postalCode, String phone) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.phone = phone;        
    }
    
    //Getters
    
    public String getAddressID() {
        return addressId;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public String getCityID() {
        return cityId;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getCountry() {
        return country;
    }
    
    public String getPostal() {
        return postalCode;
    }
    
    public String getPhone() {
        return phone;
    }   
    
    
    //Setters
    
    public void setAddressID(String addressId){
        this.addressId = addressId;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public void setCityID(String cityId) {
        this.cityId = cityId;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public void setPostal(String postalCode){
        this.postalCode = postalCode;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
   
    
}
