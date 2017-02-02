package contactapp;

import java.io.Serializable;

public class ContactModelClass implements Serializable {
	
	private String contactName;
	private String contactNumber;
	
	public ContactModelClass(String name,String number) {
		this.contactName =  name;
		this.contactNumber = number;
	}
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public void printContacts()
	{
		System.out.println("Contact Name : "+contactName);
		System.out.println("Contact Number :"+contactNumber);
	}
	
}
