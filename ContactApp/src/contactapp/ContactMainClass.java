package contactapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ContactMainClass {
	private static final String FILENAME = "ContactFile.txt";
	static HashMap<String, ContactModelClass> mapOfContacts;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		if (mapOfContacts==null) {
			mapOfContacts = readfromFile();
		}
		int userInput;
		System.out.println("1. Add");
		System.out.println("2. Edit");
		System.out.println("3. Delete");
		System.out.println("4. Show Contacts");
		System.out.println("5. Search Contacts");
		System.out.println("6. Exit");
		System.out.println("Enter Your Choice");
		userInput = sc.nextInt();
		switch (userInput) {
		case 1:
			addContactToFile();
			break;
		case 2:
			updateContact();
			break;
		case 3:
			deleteContactFromFile();
			break;
		case 4:
			getContactFromFile();
			break;
		case 5:
			searchContactFromFile();
			break;
		default:
			System.exit(0);
			break;
		}
	}

	private static void searchContactFromFile() {
		// TODO Auto-generated method stub
		if (mapOfContacts !=null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter the contact name to be searched");
				String searchContact = br.readLine();
				ContactModelClass contact = mapOfContacts.get(searchContact);
				if (contact !=null) {
					contact.printContacts();
				}else
				{
					System.out.println("No data found");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	private static void updateContact() {
		// TODO Auto-generated method stub
		int mapsize = mapOfContacts.size();
		if (mapsize == 0) {
			System.out.println("No data found");
		}
		else if (mapOfContacts !=null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter the contact name to be updated");
				String updateContact = br.readLine();
				if (mapOfContacts.containsKey(updateContact)) {
					System.out.println("Enter the new contact number");
					String updateContactNumber = br.readLine();
					ContactModelClass contacts = new ContactModelClass(updateContact,updateContactNumber);
					mapOfContacts.put(updateContact, contacts);
					writeToFile(mapOfContacts);	
				}else
				{
					System.out.println("The contact "+updateContact+" not found!!");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private static void deleteContactFromFile() {
		// TODO Auto-generated method stub
		int mapsize = mapOfContacts.size();
		if (mapsize == 0) {
			System.out.println("No data found");
		}
		else if (mapOfContacts !=null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter the Contact Name to delete ");
				String contactToDelete = br.readLine();
				mapOfContacts.remove(contactToDelete);
				if (mapOfContacts.size()<mapsize) {
					writeToFile(mapOfContacts);
					System.out.println("The Contact successfully deleted.");
				}else{
					System.out.println("Not data found. Please try again.");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private static HashMap<String, ContactModelClass> readfromFile()
	{
		HashMap<String, ContactModelClass> contactobject = null;
		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream objectInputStream = new ObjectInputStream(fis);
			contactobject = (HashMap<String, ContactModelClass>) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return contactobject;
	}

	private static void getContactFromFile() {
		// TODO Auto-generated method stub
		try {
		if (mapOfContacts !=null) {
			Set<String> setOfContacts = mapOfContacts.keySet();
			Iterator<String>iterator = setOfContacts.iterator();
			while (iterator.hasNext()) {
				ContactModelClass contatcs = mapOfContacts.get(iterator.next());
				contatcs.printContacts();
			}
		}else{
			System.out.println("No data found");
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeToFile(HashMap<String, ContactModelClass>contactmap)
	{
		try {
			FileOutputStream fos;
			fos = new FileOutputStream("ContactFile.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(contactmap);
			oos.flush();
			oos.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void addContactToFile() {
		// TODO Auto-generated method stub
		if (mapOfContacts ==null) {
			mapOfContacts = new HashMap<String,ContactModelClass>();
		}
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please Enter Contact Name");
			String userInput = bufferedReader.readLine();
			if (mapOfContacts.containsKey(userInput)) {
				System.out.println("Contact "+userInput+" already present");
			}else{
				System.out.println("Please Enter Contact Number");
				String userInputNumber = bufferedReader.readLine();
				ContactModelClass contacts = new ContactModelClass(userInput,userInputNumber);
				mapOfContacts.put(userInput, contacts);
				writeToFile(mapOfContacts);
				System.out.println("Contact "+userInput+" has been saved");
			}
						
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
