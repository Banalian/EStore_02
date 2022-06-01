package edu.polytech.estore.ws;

import edu.polytech.estore.business.StoreBusinessLocal;
import edu.polytech.estore.model.Comment;
import edu.polytech.estore.model.Product;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Stateless
@Path("v1")
public class EStoreRest {

    @EJB
    private StoreBusinessLocal business;

    /**
     * Query 1 and 5 : get all products (5 : with the user's requested currency)
     * @param currency the currency to convert to
     * @return the list of products
     */
    @Path("/products")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Product> getProducts(@QueryParam("currency") String currency) {
        List<Product> products;
        if(currency != null) {
            products = business.getProducts(currency);
        } else {
            products = business.getProducts();
        }
        return products;
    }

    /**
     * Query 6 : Delete a product and all its comments
     * @param productId The product id to delete
     */
    @Path("/products/{productId}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteProduct(@PathParam("productId") Long productId) {
        business.deleteProduct(productId);
    }

    /**
     * Query 7 : Create a new product
     * @param product The product to create
     */
    @Path("/products")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void createProduct(Product product) {
        business.createProduct(product);
    }

    /**
     * Query 8 : Complete modification of a product
     * @param product The product to update
     */
    @Path("/products")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void updateProduct(Product product) {
        business.updateProduct(product);
    }

    /**
     * Query 9 : Partial modification of a product
     * @param productId The product id to update
     * @param product The product to update
     */
    @Path("/products/{productId}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void partialUpdateProduct(@PathParam("productId") Long productId, Product product) {
        business.patchProduct(productId, product);
    }

    /**
     * Query 10 : Get all the comments of a product
     * @param productId The product id
     * @return The list of comments
     */
    @Path("/products/{productId}/comments")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Comment> getComments(@PathParam("productId") Long productId) {
        return business.getProductComments(productId);
    }
}
