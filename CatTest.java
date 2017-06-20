import org.junit.*;
import static org.junit.Assert.*;
import org.mockito.*;
public class CatTest{
  Cat test;
  @Before
  public void init(){
    test = new Cat("Sir Mittens I", 100000);
  }
  //  Makes sure that a cat was successfully given an owner
  @Test
  public void testHumanTransfer(){
    Customer cust = Mockito.mock(Customer.class);
    test.rentCat(cust);
    assertEquals(cust, test.getHuman());
  }
  //  Makes sure that that the cat is no longer available for rent
  @Test
  public void testUnavailable(){
    Customer cust = Mockito.mock(Customer.class);
    test.rentCat(cust);
    assertFalse(test.isAvailable());
  }
  //  Ensures that a reference to the cats owner was returned successfully.
  @Test
  public void testReturn(){
    Customer cust = Mockito.mock(Customer.class);
    test.customer = cust;
    Customer expected = test.returnCat();
    assertEquals(expected, cust);
  }
  /*
  *   Ensures that renting a cat leaves that cat unavailable to be rented again
  *   before being returned.
  */
  @Test
  public void testAvailable(){
    Customer cust = Mockito.mock(Customer.class);
    test.customer = cust;
    Customer expected = test.returnCat();
    assertTrue(test.isAvailable());
  }
}
