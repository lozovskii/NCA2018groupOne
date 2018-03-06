
export interface OrderStatus {
  id: number,
  name: string,
  description: string
}


export const ORDER_STATUSES: OrderStatus[] =  [
  {id: 1, name: "DRAFT", description: ""},
  {id: 2, name: "CANCELLED", description: ""},
  {id: 3, name: "POSTPONED", description: ""},
  {id: 4, name: "ASSOCIATED", description: ""},
  {id: 5, name: "PROCESSING", description: ""},
  {id: 6, name: "OPEN", description: "OPEN"},
  {id: 7, name: "CONFIRMED", description: ""},
  {id: 8, name: "DELIVERING", description: ""},
  {id: 9, name: "DELIVERED", description: ""},
  {id: 10, name: "WAITING_FOR_FEEDBACK", description: ""},
  {id: 11, name: "FEEDBACK_REVIEWED", description: ""}
];
