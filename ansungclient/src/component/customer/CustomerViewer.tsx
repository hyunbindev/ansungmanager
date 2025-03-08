import { useEffect, useState } from "react";
import NaverMap, { LatLng } from "../map/NaverMap";
import style from './customer.module.css'
import { Customer } from "../../entity/Customer";

interface Props{
    selectedCustomer:Customer|undefined;
}

const CustomerViewer:React.FC<Props> = ({selectedCustomer})=>{
    const [location,setLocation] = useState<LatLng|null>(null);

    useEffect(()=>{
        if(selectedCustomer && selectedCustomer.lat && selectedCustomer.lng){
            setLocation({Lat:selectedCustomer.lat,Lng:selectedCustomer.lng});
        }else{
            setLocation(null);
        }
    },[selectedCustomer])
    return(
        <div id={style.customer_editor}>
            <NaverMap location={location}/>
            <div id={style.info}>
                <div id={style.tel}>{selectedCustomer?.tel}</div>
                <div id={style.address}>{selectedCustomer?.address}</div>
                <div id={style.remarks}>{selectedCustomer?.remarks}</div>
            </div>
        </div>
    )
}
export default CustomerViewer;