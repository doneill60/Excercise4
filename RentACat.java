import java.util.Scanner;
import java.util.ArrayList;
import org.mockito.*;
public class RentACat{
  ArrayList<Cat> cats;
  public boolean anyAvailable;
  public IOControl ioc;
  ArrayList<Customer> customers;
  Scanner in = new Scanner(System.in);
  public RentACat(ArrayList<Cat> cats, ArrayList<Customer> customers, IOControl ioc){
    this.cats = cats;
    this.customers = customers;
    this.ioc = ioc;
    anyAvailable = false;
  }
  /*
  *
  * This is essentially my main method. Im gonna treat it as my main method and
  * just test the public methods it depends on.
  *
  */
  public void run(){
    populateData();
    while(true){
      ioc.print("\nPlease select an option: \n1. See a list of cats for rent"
      + "\n2. Rent a cat to a customer" + "\n3. Return a cat" + "\n4. Quit");
      int selection = ioc.getInt();
      switch(selection){
        case 1:
          printAvailableCats();
          break;
        case 2:
          rent();
          break;
        case 3:
          returnCat();
          break;
        case 4:
          ioc.print("Bye");
          System.exit(0);
          break;
        default:
          ioc.print("Not an option");
          break;
      }
    }
  }
  /*
  * Populates the cat and customer lists with fake data.
  */
  public void populateData(){
    cats.add(new Cat("Senor Delgato", 50));
    cats.add(new Cat("Snowball", 55));
    cats.add(new Cat("Sir Sniffles", 1000));
    cats.add(new Cat("Gazorpazorpfield", 0));
    customers.add(new Customer("Jan Michael Vincent"));
    customers.add(new Customer("Charlie"));
    customers.add(new Customer("Curtis Jackson"));
    customers.add(new Customer("Seal"));
  }
  /*
  * Prints all available cats for rent
  */
  public void printAvailableCats(){
    anyAvailable = false;
    ioc.print("\nCats for rent:\nsize: ");
    for(int i = 0; i<cats.size(); i++){
      if(cats.get(i).isAvailable()){
        ioc.print("ID " + i + ": " + cats.get(i).toString());
        anyAvailable = true;
      }
    }
    if(!anyAvailable){
      ioc.print("No Cats Here!");
    }
  }

  /*
  * Rents a cat to a customer provided the customer is valid, the cat is valid,
  * and the cat is not already being rented.
  *
  * Please Note: All for loops are only for tesing as leaving them as while
  * loops would cause an infinite loop
  */
  public void rent(){
    int customerId = -1;
    int catId = -1;
    for(int i = 0; i < 5; i++){
      ioc.print("Customer ID: ");
      customerId = ioc.getInt();
      if(customerId >= customers.size() || customerId<0){
        customerId = -1;
        ioc.print("Not a valid id number: ");
        continue;
      }
      break;
    }
    if(customerId == -1){return;}
    for(int i= 0; i<5; i++){
      ioc.print("Rent which cat? ");
      catId = ioc.getInt();
      if(catId >= cats.size() || catId<0){
        ioc.print("Not a valid id number: ");
        catId = -1;
        continue;
      }
      else{
        if(cats.get(catId).isAvailable()){
          cats.get(catId).rentCat(customers.get(customerId));
          ioc.print(cats.get(catId).getName() + " has been rented to " + customers.get(customerId).toString());
          break;
        }
        else{
          ioc.print("Sorry " + cats.get(catId).getName()+ " has already been rented away.");
        }
      }
    }
  }
  /*
  * Returns a cat provided the customer is valid, the cat is valid,
  * and the cat is not already in store.
  *
  * Please Note: Again, all for loops are only for tesing as leaving them as while
  * loops would cause an infinite loop
  */
  public void returnCat(){
    int catId;
    for(int i = 0; i < 5; i++){
      ioc.print("Return which cat?");
      catId = ioc.getInt();
      if(catId >= cats.size() || catId<0){
        ioc.print("Not a valid id number.");
        catId = -1;
        continue;
      }
      else{
        if(cats.get(catId).isAvailable()){
          ioc.print("That cat is still here, how did you get that?");
          continue;
        }
        else{
          if(!cats.get(catId).isAvailable()){
            Customer returner = cats.get(catId).returnCat();
            ioc.print(returner.toString() + " paid $" + cats.get(catId).getPrice());
            ioc.print("Welcome back " + cats.get(catId).getName());
            break;
          }
        }
      }
    }
  }
}
