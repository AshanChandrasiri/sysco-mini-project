package com.sysco.miniproject.service.impl;

import com.sysco.miniproject.data.dao.Cart;
import com.sysco.miniproject.data.dao.CartProduct;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.data.dao.User;
import com.sysco.miniproject.data.dto.request.AddToCartDto;
import com.sysco.miniproject.data.dto.request.CreateCartDto;
import com.sysco.miniproject.data.dto.response.CartProductViewDto;
import com.sysco.miniproject.data.dto.response.CartViewDto;
import com.sysco.miniproject.respository.CartProductRepository;
import com.sysco.miniproject.respository.CartRepository;
import com.sysco.miniproject.respository.models.CartDetails;
import com.sysco.miniproject.service.AuthService;
import com.sysco.miniproject.service.CartService;
import com.sysco.miniproject.service.ProductService;
import com.sysco.miniproject.shared.Utils;
import com.sysco.miniproject.shared.exceptions.BadRequestException;
import com.sysco.miniproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("cartServiceImpl")
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartProductRepository cartProductRepository;

    private final AuthService authService;

    private final ProductService productService;


    @Override
    @Transactional
    public Cart createCart(CreateCartDto dto) {

        Cart cart = new Cart();
        cart.setName(dto.getName());
        cart.setUser(authService.getContextUser());
        cart.setOpen(true);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public CartProduct addToCard(AddToCartDto req) {

        User currentUser = authService.getContextUser();

        CartProduct cartProduct = cartProductRepository.findByCartIdAndCartUserIdAndProductId(req.getCartId(), currentUser.getId(), req.getProductId())
                .orElseGet(() -> {
                    CartProduct cp = new CartProduct();
                    cp.setCart(getUserCartById(req.getCartId(), currentUser.getId()));
                    cp.setProduct(productService.getProductById(req.getProductId()));
                    cp.setQuantity(0);
                    return cp;
                });

        int newQuantity = cartProduct.getQuantity() + req.getQuantity();
        cartProduct.setQuantity(newQuantity);

        return cartProductRepository.save(cartProduct);
    }

    @Override
    @Transactional
    public void removeFromCart(Long cartId, Long productId) {
        cartProductRepository.deleteByCartIdAndProductId(cartId, productId);
    }

    @Override
    public CartViewDto viewCart(Long cartId) {

        User currentUser = authService.getContextUser();

        Cart cart = getUserCartById(cartId, currentUser.getId());

        CartViewDto dto = new CartViewDto();
        dto.setClosed(cart.isOpen());
        dto.setName(cart.getName());
        dto.setId(cart.getId());

        List<CartProductViewDto> products = cart.getCartProducts()
                .stream().map(this::convert).collect(Collectors.toList());

        dto.setProducts(products);
        dto.setTotalItems(cart.getCartProducts().size());
        dto.setTotalPrice(Utils.calculateTotal(cart.getCartProducts()));

        return dto;
    }

    @Override
    public List<CartDetails> getUserCarts() {
        User currentUser = authService.getContextUser();
        return cartRepository
                .getUsersAllCarts(currentUser.getId());
    }

    @Override
    public void removeCart(Long cartId) {
        User currentUser = authService.getContextUser();
        cartRepository.deleteByIdAndUserId(cartId, currentUser.getId());
    }

    private CartProductViewDto convert(CartProduct cp) {
        CartProductViewDto dto = new CartProductViewDto();
        Product product = cp.getProduct();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setImage(product.getImage());
        dto.setUnitPrice(product.getPrice());
        dto.setQuantity(cp.getQuantity());
        return dto;
    }

    private Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Invalid cart"));
    }

    private Cart getUserCartById(Long cartId, Long userId) {

        return cartRepository.findByIdAndUserId(cartId, userId)
                .orElseThrow(() -> new NotFoundException("Cart does not found for user"));

    }
}
