package ncadvanced2018.groupeone.parent.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import ncadvanced2018.groupeone.parent.model.entity.OrderStatus;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConfirmOrderEvent extends ApplicationEvent {

    private Order originalOrder;
    private Order updatedOrder;
    private boolean changedToConfirmedStatus;

    public ConfirmOrderEvent(Object source, Order originalOrder, Order updatedOrder) {
        super(source);
        this.originalOrder = originalOrder;
        this.updatedOrder = updatedOrder;
        changedToConfirmedStatus = originalOrder != null && originalOrder.getOrderStatus() != OrderStatus.CONFIRMED &&
                updatedOrder.getOrderStatus() == OrderStatus.CONFIRMED;
    }

}
