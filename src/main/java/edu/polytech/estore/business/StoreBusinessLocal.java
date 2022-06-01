package edu.polytech.estore.business;

import java.util.List;

import javax.ejb.Local;

import edu.polytech.estore.model.Comment;
import edu.polytech.estore.model.Product;

@Local
public interface StoreBusinessLocal {

    /**
     * Pour le service nï¿½1.
     */
    public List<Product> getProducts();

    /**
     * Pour le service nï¿½2.
     * 
     * @param productId L'identifiant du produit.
     */
    public Product getProduct(Long productId);

    /**
     * Pour le service nï¿½3.
     * 
     * @param category La catï¿½gorie sur laquelle filtrer.
     */
    public List<Product> getProductsOfCategory(String category);

    /**
     * Pour le service nï¿½4.
     * 
     * @param asc Si <code>true</code>, le tri est ascendant sur le prix, si
     *            <code>false</code>, le tri est descendant.
     */
    public List<Product> getSortedProducts(Boolean asc);

    public List<Product> getProducts(String category , String currency , Boolean sort);

    /**
     * Pour le service nï¿½4.
     * 
     * @param category La catï¿½gorie sur laquelle filtrer.
     * @param asc      Si <code>true</code>, le tri est ascendant sur le prix, si
     *                 <code>false</code>, le tri est descendant.
     */
    public List<Product> getSortedProductsOfCategory(String category, Boolean asc);

    /**
     * Pour le service nï¿½6.
     * 
     * @param productId L'identiant du produit ï¿½ supprimer.
     */
    public void deleteProduct(Long productId);

    /**
     * Pour le serivce nï¿½7.
     * 
     * @param product Le produit ï¿½ crï¿½er.
     */
    public void createProduct(Product product);

    /**
     * Pour le serivce nï¿½8.
     * 
     * @param product Le produit ï¿½ modifier (modification totale).
     */
    public void updateProduct(Product product);

    /**
     * Pour le serivce nï¿½9.
     * 
     * @param productId L'identifiant du produit ï¿½ modifier (modification
     *                  partielle).
     * @param patch     Les modifications ï¿½ apporter.
     */
    public void patchProduct(Long productId, Product patch);

    /**
     * Pour le serivce nï¿½10.
     * 
     * @param productId L'identifiant du produit dont on souhaite rï¿½cupï¿½rer les
     *                  commentaires.
     */
    public List<Comment> getProductComments(Long productId);

    /**
     * Pour le serivce n°5.
     *
     * @param currency La devise dans laquelle le prix doit être affiché.
     */
    public List<Product> getProducts(String currency);
}
