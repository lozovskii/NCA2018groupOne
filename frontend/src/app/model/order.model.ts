import { Address } from "./address.model";
import { User } from "./user.model";
import { Office } from "./office.model";
import { OrderStatus } from "./orderStatus.model"; 

export interface Order {
    id: number;
    office: Office;
    user: User;
    orderStatus: OrderStatus;
    receiverAddress: Address;
    senderAddress: Address;
    // date?
    description: string;
    feedback: string;
}