import axios from "axios"
import { Customer } from "../entity/Customer"
import { useState } from "react"

const useCustomer = () =>{
    const [customers ,setCustomers] = useState<Customer[]>([]);
    const postNewCustomer=(customer:Customer)=>{
        axios.post("http://localhost:8080/api/customer",customer)
        .then(res=>console.log(res))
        .catch(err=>console.error(err));
    }
    const getCustomer=()=>{
        axios.get<Customer[]>("http://localhost:8080/api/customer")
        .then((res)=>{setCustomers(res.data)})
        .catch(err=>console.error(err));
    }
    return {postNewCustomer , getCustomer , customers}
}
export default useCustomer;