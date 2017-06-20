import java.util.ArrayList;
public class RentACatDriver{
  public static void main(String[] args){
    ArrayList<Cat> cats = new ArrayList<Cat>();
    ArrayList<Customer> customers = new ArrayList<Customer>();
    IOControl ioc = new IOControl(System.in, System.out);
    RentACat r = new RentACat(cats, customers, ioc);
    r.run();
  }
}
