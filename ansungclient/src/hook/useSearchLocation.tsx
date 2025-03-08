import axios from "axios"
import { useState } from "react"
import { LatLng } from "../component/map/NaverMap"

export interface location{
    LatLng:LatLng;
    address:string;
}

const useSearchLocation = () =>{
    const [locations , setLocations] = useState<location[]>([]);
    
    const [selectedLocation , setSelectedLocation] = useState<location | undefined>();

    const searchLocation = (address:string)=>{
        axios.get(`http://localhost:8080/api/geocoder?address=${address}`)
        .then((res)=>{
            const newLocations:location[] = res.data.addresses.map((address:any)=>({LatLng:{Lat:address.y , Lng:address.x} , address:address.roadAddress}));
            console.log(res.data);
            setLocations(newLocations);
        })
        .catch((err)=>console.error(err));
    }
    return {searchLocation , locations ,selectedLocation ,setSelectedLocation};
}
export default useSearchLocation;