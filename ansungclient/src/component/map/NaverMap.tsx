import React, { useEffect, useRef, useState } from "react";
import { location } from "../../hook/useSearchLocation";

import destinationIcon from '../../assets/destination.svg';
import startpointIcon from '../../assets/startpointIcon.svg';
import customer_add from '../../assets/customer_add.svg'
const NAVER_CLIENT_ID = "euadrjy4sx"; // 네이버 API 클라이언트 ID

interface props{
  location:LatLng|null
  locations?:location[]
  setSelectLocation?:React.Dispatch<React.SetStateAction<location | undefined>>
}

export interface LatLng{
  Lat:number;
  Lng:number;
}

const NaverMap: React.FC<props> = ({location,locations,setSelectLocation}) => {
  const mapElement = useRef<HTMLDivElement | null>(null);
  const mapRef = useRef<naver.maps.Map| undefined>(undefined);
  const markers = useRef<naver.maps.Marker[]>([]);
  const locationMarker = useRef<naver.maps.Marker | null>(null);
  const shopMarker = useRef<naver.maps.Marker | null>(null);
  const line = useRef<naver.maps.Polyline | null>(null);
  useEffect(() => {
    const script = document.createElement("script");
    script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${NAVER_CLIENT_ID}`;
    script.async = true;
    script.onload = () => {
      if (!mapElement.current) return;

      mapRef.current = new naver.maps.Map(mapElement.current, {
        center: new naver.maps.LatLng(37.57762, 127.069400), // 서울 시청 좌표
        zoom: 18,
      });
      //안성상회 위치 마커
      shopMarker.current = new naver.maps.Marker({
        position:new naver.maps.LatLng(37.57762, 127.069400),
        map:mapRef.current,
        title:"안성상회",
        icon:{
          url:startpointIcon,
          scaledSize: new naver.maps.Size(40,40),
          anchor: new naver.maps.Point(20, 20),
        }
      });
    };
    document.head.appendChild(script);
  }, []);

  useEffect(()=>{
    if(location && mapRef.current){
      const centerLocation = new naver.maps.LatLng(location.Lat , location.Lng);
      mapRef.current.panTo(centerLocation);
      locationMarker.current?.setMap(null);
      line.current?.setMap(null);
      locationMarker.current = new naver.maps.Marker({
        position: centerLocation,
        map:mapRef.current,
        icon:{
          url:destinationIcon,
          scaledSize: new naver.maps.Size(40,40),
          anchor: new naver.maps.Point(20, 20),
        }
      })
      line.current = new naver.maps.Polyline({
        path:[new naver.maps.LatLng(37.57762, 127.069400),new naver.maps.LatLng(location.Lat,location.Lng)],
        strokeColor: "blue",
        strokeWeight: 3,
      });
      line.current.setMap(mapRef.current);
    }else{
      locationMarker.current?.setMap(null);
      line.current?.setMap(null);
    }
  },[location])
  
  useEffect(()=>{
    if(mapRef.current){
      markers.current.forEach((marker) => marker.setMap(null));
      locations?.forEach((location)=>{
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(location.LatLng.Lat,location.LatLng.Lng),
          map:mapRef.current,
          icon:{
            url:customer_add,
            scaledSize: new naver.maps.Size(40,40),
            anchor: new naver.maps.Point(20, 20),
          }
        })
        if(setSelectLocation){
          marker.addListener('click',()=>{setSelectLocation(location)})
        }
        const centerLocaation = new naver.maps.LatLng(locations[0].LatLng.Lat , locations[0].LatLng.Lng);
        mapRef.current?.panTo(centerLocaation);
        markers.current.push(marker);
      })
    }
  },[locations])

  return <div ref={mapElement} style={{ width: "100%", height: "400px" }} />;
};

export default NaverMap;
