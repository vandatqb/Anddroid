package com.example.phanv.virus.Model;

/**
 * Created by phanv on 25-Oct-17.
 */

public class Contact {
    private String nameContact;
    private String phoneNumber;

    public Contact(String nameContact, String phoneNumber) {
        this.nameContact = nameContact;
        this.phoneNumber = phoneNumber;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNameContact() {
        return nameContact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
