package mobi.dashuxia.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Customer  {

    private long id;
    @NonNull
    private String openCode;
    @NonNull
    private String name;
    @NonNull
    private String mobilePhone;

    private String address;

    private boolean isActivated = false;

    private Long cartId;
    
    public Customer() {}
    
    public Customer(Long id, String mobilePhone, String name, String address,
            Long cartId, String openCode, Boolean isActivated) {
        this.id = id;
        this.mobilePhone = mobilePhone;
        this.name = name;
        this.address = address;
        this.cartId = cartId;
        this.openCode = openCode;
        this.isActivated = isActivated;
    }
}
