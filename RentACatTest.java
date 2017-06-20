import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

public class RentACatTest{
  RentACat rac;
  Cat testCat;
  Customer testCustomer;
  IOControl ioc;
  @Mock ArrayList<Cat> cats;
  @Mock ArrayList<Customer> customers;
  @Before
  public void init(){
    MockitoAnnotations.initMocks(this);
    ioc = mock(IOControl.class);
    rac = new RentACat(cats, customers, ioc);
    rac.populateData();
    testCat = mock(Cat.class);
    testCustomer = mock(Customer.class);
  }

  //  Ensures that 4 cats were added to the system.
  @Test
  public void testCatsAdded(){
    ArgumentCaptor<Cat> argument = ArgumentCaptor.forClass(Cat.class);
    //Doesnt generically accept any object, only that of cats.add(Cat)
    Mockito.verify(cats, times(4)).add(argument.capture());
  }
  //  Ensures that 4 customers were added to the system
  @Test
  public void testCustomersAdded(){
    ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);
    //Doesnt generically accept any object, only that of customers.add(Customer)
    Mockito.verify(customers, times(4)).add(argument.capture());
  }
  //  Asserts that no cats are available for rent when all have been rented away
  @Test
  public void testNoCatPrint(){
    when(testCat.isAvailable()).thenReturn(false);
    when(cats.size()).thenReturn(4);
    //  When mock ArrayList asks for any cat, return our mock cat object
    when(cats.get(anyInt())).thenReturn(testCat);
    rac.printAvailableCats();
    assertFalse(rac.anyAvailable);
  }
  //  Tests happy case of printing a list of available cats
  @Test
  public void testHappyPrint(){
    when(testCat.isAvailable()).thenReturn(true);
    when(cats.size()).thenReturn(4);
    when(cats.get(anyInt())).thenReturn(testCat);
    rac.printAvailableCats();
    assertTrue(rac.anyAvailable);
  }
  //  Tests happy case of renting an available cat
  @Test
  public void testHappyRent(){
    when(customers.size()).thenReturn(4);
    when(cats.size()).thenReturn(4);
    when(cats.get(anyInt())).thenReturn(testCat);
    when(testCat.isAvailable()).thenReturn(true);
    //  When mock ArrayList asks for any customer, return our mock customer
    when(customers.get(anyInt())).thenReturn(testCustomer);
    //  Rent cat 1 to customer 1
    when(ioc.getInt()).thenReturn(1);
    rac.rent();
    verify(testCat, times(1)).rentCat(testCustomer);
  }
  //  Tests attempt to rent a cat that isnt available
  @Test
  public void testCatUnavailable(){
    when(customers.size()).thenReturn(4);
    when(cats.size()).thenReturn(4);
    when(cats.get(anyInt())).thenReturn(testCat);
    when(testCat.isAvailable()).thenReturn(false);
    when(customers.get(anyInt())).thenReturn(testCustomer);
    when(ioc.getInt()).thenReturn(1);
    rac.rent();
    verify(testCat, never()).rentCat(testCustomer);
  }
  /*
  * Tests that attempting to rent to an invalid customer will not
  * make it to the prompt asking the customer about which cat he would like
  */
  @Test
  public void testInvalidCustomer(){
    when(customers.size()).thenReturn(4);
    when(cats.size()).thenReturn(4);
    when(cats.get(anyInt())).thenReturn(testCat);
    when(testCat.isAvailable()).thenReturn(false);
    when(customers.get(anyInt())).thenReturn(testCustomer);
    //  Tests renting to customer 5 (dne)
    when(ioc.getInt()).thenReturn(5);
    rac.rent();
    //  Verifies that the system never even inquires about any cats
    verify(cats, never()).get(anyInt());
  }
  /*
  * Verifies that a valid customer is not able to rent an invalid cat
  */
  @Test
  public void testInvalidCat(){
    when(customers.size()).thenReturn(5);
    when(cats.size()).thenReturn(4);
    when(cats.get(anyInt())).thenReturn(testCat);
    when(testCat.isAvailable()).thenReturn(false);
    when(customers.get(anyInt())).thenReturn(testCustomer);
    when(ioc.getInt()).thenReturn(5);
    rac.rent();
    verify(testCat, never()).rentCat(testCustomer);
  }
  /*
  * Tests the happy case of a proper customer returning a proper cat
  * that isnt already in the shop
  */
  @Test
  public void testHappyReturn(){
    when(ioc.getInt()).thenReturn(1);
    when(cats.size()).thenReturn(4);
    when(cats.get(anyInt())).thenReturn(testCat);
    when(testCat.returnCat()).thenReturn(testCustomer);
    when(testCat.isAvailable()).thenReturn(false);
    rac.returnCat();
    verify(testCat, times(1)).returnCat();
  }
  /*
  * Tests the edge case of a proper customer returning a proper cat
  * that is already in the shop
  */
  @Test
  public void testCatReturnOfOwnedCat(){
    when(ioc.getInt()).thenReturn(1);
    when(cats.size()).thenReturn(4);
    when(cats.get(anyInt())).thenReturn(testCat);
    when(testCat.returnCat()).thenReturn(testCustomer);
    when(testCat.isAvailable()).thenReturn(true);
    rac.returnCat();
    verify(testCat, never()).returnCat();
  }
  //  Tests the corner case of an invalid cat being returned
  @Test
  public void testInvalidCatId(){
    when(ioc.getInt()).thenReturn(5);
    when(cats.size()).thenReturn(4);
    when(cats.get(anyInt())).thenReturn(testCat);
    when(testCat.returnCat()).thenReturn(testCustomer);
    when(testCat.isAvailable()).thenReturn(true);
    rac.returnCat();
    verify(testCat, never()).returnCat();
  }

}
