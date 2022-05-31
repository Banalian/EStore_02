package edu.polytech.estore.ws;

import edu.polytech.estore.business.StoreBusinessLocal;
import edu.polytech.estore.model.Product;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("v1")
public class EStoreRest {

    @EJB
    private StoreBusinessLocal business;

    @Path("/products")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Product> getProducts() {
        return business.getProducts();
    }
}
