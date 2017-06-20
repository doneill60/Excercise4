public class Cat{
  public Cat(String name, int cost){
    this.name = name;
    this.cost = cost;
    available = true;
  }
  private String name;
  private int cost;
  private boolean available;
  public Customer customer;

  /*
  * Here the cat keeps track of its owner.
  * It saves the RentACat from storing an additional data structure to map each
  * cat to its owner.
  */
  public void rentCat(Customer c){
    customer = c;
    available = false;
  }

  /*
  * Returns a reference to the customer so the RentACat system may know
  * who owned the cat.
  */
  public Customer returnCat(){
    Customer temp = customer;
    customer = null;
    available = true;
    return temp;
  }
  public boolean isAvailable(){
    return available;
  }
  public void setAvailability(boolean b){
    available = b;
  }
  public String getName(){
    return name;
  }
  public int getPrice(){
    return cost;
  }
  public Customer getHuman(){
    return customer;
  }
  public String toString(){
    return name + ": $" + cost + " /day";
  }
}
