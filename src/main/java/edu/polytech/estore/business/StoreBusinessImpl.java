package edu.polytech.estore.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.polytech.estore.dao.CommentDao;
import edu.polytech.estore.dao.ExchangeRateDAO;
import edu.polytech.estore.dao.ProductDao;
import edu.polytech.estore.model.Comment;
import edu.polytech.estore.model.Product;

@Stateless
public class StoreBusinessImpl implements StoreBusinessLocal {

    @Inject
    private ProductDao productDao;

    @Inject
    private CommentDao commentDao;

    @Inject
    ExchangeRateDAO exchangeRateDAO;

    @Override
    public List<Product> getProducts() {
        List<Product> products = this.productDao.getProducts();
        for (Product product : products) {
            product.setComments(this.commentDao.getComments(product.getProductId()));
            product.setPriceInCurrency(product.getPriceInEuro());
        }
        return products;
    }

    @Override
    public Product getProduct(Long productId) {
        Product product = this.productDao.getProduct(productId);
        if (null != product) {
            product.setComments(this.commentDao.getComments(productId));
            product.setPriceInCurrency(product.getPriceInEuro());
        }
        return product;
    }

    @Override
    public Product getProduct(Long productId, String currency) {
        Product product = this.productDao.getProduct(productId);
        if (null != product) {
            product.setComments(this.commentDao.getComments(productId));
            product.setPriceInCurrency(product.getPriceInEuro());

            //currency conversion
            if(currency != null){
                updateCurrency(product,currency);
            }else{
                product.setPriceInCurrency(product.getPriceInEuro());
            }
        }

        return product;
    }

    @Override
    public List<Product> getProductsOfCategory(String category) {
        return this.productDao.getProductsOfCategory(category);
    }

    /**
     * @param asc      Croissant (<code>true</code>) ou d?croissant
     *                 (<code>false</code>) ?
     * @param products La liste ? trier.
     * @return La liste en param?tre tri?e de mani?re croissante ou d?croissante.
     */
    private List<Product> sortList(Boolean asc, List<Product> products) {
        if (asc) {
            Collections.sort(products, (p1, p2) -> p1.getPriceInEuro() > p2.getPriceInEuro() ? 1
                    : p1.getPriceInEuro() == p2.getPriceInEuro() ? 0 : -1);
        } else {
            Collections.sort(products, (p1, p2) -> p1.getPriceInEuro() < p2.getPriceInEuro() ? 1
                    : p1.getPriceInEuro() == p2.getPriceInEuro() ? 0 : -1);
        }
        return products;
    }

    /**
     * Get the list of products, with different sorting/filtering options.
     * @param category the category the user wants to filter by, can be null to not filter
     * @param currency the currency the user wants the prices to convert to, can be null to not convert
     * @param sort the order of sorting chosen by user, can be null to not order
     * @return The list of products, ordered, converted and sorted if necessary
     */
    @Override
    public List<Product> getProducts(String category , String currency , Boolean sort){

        List<Product> list = new ArrayList<>();

        //checking categorization
        if(category != null){
            list = getProductsOfCategory(category);
        }
        else{
            list = getProducts();
        }

        //currency conversion
        if(currency != null){
            updateCurrencies(list, currency);
        }else{
            for(Product product : list){
                product.setPriceInCurrency(product.getPriceInEuro());
            }
        }

        //sorting the list
        if(sort != null){
            list = sortList(sort,list);
        }



        return list;
    }

    @Override
    public List<Product> getSortedProducts(Boolean asc) {
        return sortList(asc, getProducts());
    }

    @Override
    public List<Product> getSortedProductsOfCategory(String category, Boolean asc) {
        return sortList(asc, getProductsOfCategory(category));
    }

    @Override
    public void deleteProduct(Long productId) {
        this.commentDao.deleteComments(productId);
        this.productDao.deleteProduct(productId);
    }

    @Override
    public void createProduct(Product product) {
        this.productDao.createProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        this.productDao.updateProduct(product);
    }

    @Override
    public void patchProduct(Long productId, Product patch) {
        Product product = getProduct(productId);

        if (null != patch.getCategory()) {
            product.setCategory(patch.getCategory());
        }
        if (null != patch.getLabel()) {
            product.setLabel(patch.getLabel());
        }
        if (null != patch.getPriceInEuro()) {
            product.setPriceInEuro(patch.getPriceInEuro());
        }
        if (null != patch.getStock()) {
            product.setStock(patch.getStock());
        }
        this.productDao.updateProduct(product);
    }

    @Override
    public List<Comment> getProductComments(Long productId) {
        return this.commentDao.getComments(productId);
    }

    /**
     * Pour le serivce n?5.
     *
     * @param currency La devise dans laquelle le prix doit ?tre affich?.
     */
    @Override
    public List<Product> getProducts(String currency){
        List<Product> products = this.productDao.getProducts();
        updateCurrencies(products, currency);
        return products;
    }

    /**
     * Pour le serivce n?5. Modifie le prix d'un produit selon la devise entr?e.
     * @param product Le produit ? convertir.
     * @param currency La devise dans laquelle le prix doit ?tre affich?.
     */
    @Override
    public void updateCurrency(Product product, String currency){
        if(product.getPriceInEuro() != null && product.getPriceInEuro() != 0){
            double price = exchangeRateDAO.getConvertion(currency, product.getPriceInEuro()).getResult();
            product.setPriceInCurrency(price);
        }
    }

    /**
     * Pour le serivce n?5. Modifie le prix de plusieurs produits selon la devise.
     * @param products La liste des produits.
     * @param currency La devise dans laquelle le prix doit ?tre affich?.
     */
    @Override
    public void updateCurrencies(List<Product> products, String currency){

        if(products == null || products.isEmpty()){
            return;
        }

        double rate = getCurrencyRate("EUR", currency);
        for(Product product : products){
            if(product.getPriceInEuro() != null && product.getPriceInEuro() != 0){
                double price = product.getPriceInEuro() * rate;
                product.setPriceInCurrency(price);
            }
        }
    }


    @Override
    public double getCurrencyRate(String from, String to){
        return exchangeRateDAO.getConvertionRate(from, to);
    }

}
