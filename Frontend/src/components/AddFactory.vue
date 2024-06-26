<template>
    <div class="container-fluid contact py-6 wow bounceInUp" data-wow-delay="0.1s">
        <div class="container">
            <div class="p-5 bg-light rounded contact-form">
                <div class="row g-4">
                    <div class="col-12">
                        <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Factory Creation</small>
                        <h1 class="display-5 mb-0">Create a new factory!<input type="text" class="form-control border-primary bg-light p-2 my-2" placeholder="Choose a Name" v-model="factory.name"/></h1>
                    </div>
                    <div class="col-md-6 col-lg-7">
                        <input type="file" @change="handleFileUpload" accept="image/png" class="w-100 form-control p-3 mb-4 border-primary">
                        <div class="d-inline-flex w-100 border border-primary p-4 pb-1 rounded">
                            <i class="fa fa-clock fa-2x text-primary me-4"></i>
                            <div class="">
                                <h4>Working hours</h4>
                                <div class="row">
                                    <div class="col-6">
                                        <input type="time" class="w-100 form-control mb-4 border-primary bg-light" placeholder="Start" v-model="factory.openTime">
                                    </div>
                                    <div class="col-6">
                                        <input type="time" class="w-100 form-control border-primary bg-light" placeholder="End" v-model="factory.closeTime">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="d-inline-flex w-100 border border-primary p-4 pb-1 rounded my-4">
                            <i class="fas fa-map-marker-alt fa-2x text-primary me-4"></i>
                            <div class="">
                                <h4>Address</h4>
                                <p>{{ factory.address.street }}, {{ factory.address.city }}, {{ factory.address.state }}</p>
                            </div>
                        </div>
                        
                    </div>
                    <div class="col-md-6 col-lg-5">     
                        <div id="map" class="map d-inline-flex w-100 border border-primary p-4 rounded mb-4"></div>  
                    </div>
                    <div class="col-4"/>
                    <div class="col-4">
                        <button @click="saveImage" class="w-100 btn btn-primary form-control border-primary bg-primary rounded-pill" type="submit">Submit Now</button>
                    </div>
                    <div class="col-4"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue';
    import axios from 'axios';
    import 'ol/ol.css';
    import Map from 'ol/Map';
    import View from 'ol/View';
    import TileLayer from 'ol/layer/Tile';
    import OSM from 'ol/source/OSM';
    import { fromLonLat, toLonLat } from 'ol/proj';
    import { Vector as VectorLayer } from 'ol/layer';
    import { Vector as VectorSource } from 'ol/source';
    import Feature from 'ol/Feature';
    import Point from 'ol/geom/Point';
    import { Icon, Style } from 'ol/style';
    import locationDot from '../img/location-dot-solid.svg';

    let map, vectorSource, vectorLayer;

    const factory = ref({
        name: '',
        openTime: '',
        closeTime: '',
        status: 'CLOSED',
        imagePath: '',
        rating: 0,
        address: {
            street: '',
            city: '',
            state: '',
            zip: 0
        },
        location: {
            latitude: 0,
            longitude: 0
        }
    });

    const selectedImage = ref(null);

    function handleFileUpload(event){
        selectedImage.value = event.target.files[0];
    }

    function saveImage(event){
        event.preventDefault();

        const formData = new FormData();
        formData.append('file', selectedImage.value);
        axios.post('http://localhost:8080/WebShopAppREST/rest/file/image', formData, {
            headers: {
                    'Content-Type': 'multipart/form-data'
            }
        }).then( response => {
            if (response.status === 200) {
                factory.value.imagePath = response.data;
                saveFactory();
            }
        }).catch(error => {
            if (error.response.status === 400) 
                console.error("Bad Request:", error.response.data);          
            else
                alert('Image not saved')     
        })
    }

    function saveFactory(){
        console.log(factory.value);
        axios.post('http://localhost:8080/WebShopAppREST/rest/factory/add', factory.value, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwtToken')}` // Include the Authorization header
            }}).then( response => {
            if (response.status === 200){
                alert('Factory created successfully!');
                //emit
            }        
        }).catch(error => {
            console.error(error.response.data);
        })
    }

    onMounted(() => {
        initializeMap();
    });

    const initializeMap = () => {
        vectorSource = new VectorSource();
        vectorLayer = new VectorLayer({
            source: vectorSource,
        });

        map = new Map({
            target: 'map',
            layers: [
                new TileLayer({
                    source: new OSM(),
                }),
                vectorLayer,
            ],
            view: new View({
                center: fromLonLat([0, 0]),
                zoom: 2,
            }),
        });

        map.on('click', function (evt) {
            const coordinates = toLonLat(evt.coordinate);
            const [lon, lat] = coordinates;

            // Update factory location values
            factory.value.location.longitude = lon;
            factory.value.location.latitude = lat;

            // Perform reverse geocoding to get the address
            reverseGeocode(lon, lat);

            // Update vector layer with new point feature
            vectorSource.clear();
            const point = new Point(fromLonLat([lon, lat]));
            const feature = new Feature(point);
            feature.setStyle(
                new Style({
                    image: new Icon({
                        src: locationDot, // Path to your marker icon image
                        anchor: [0.5, 1],
                        scale: 1.5, // Adjust the scale if necessary
                    }),
                })
            );
            vectorSource.addFeature(feature);
        });
    };

    const reverseGeocode = (lon, lat) => {
        console.log(lon);
        console.log(lat);
        axios.get(`https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lon}`)
            .then(response => {
                if (response.data && response.data.address) {
                    console.log(response.data.address)
                    factory.value.address.city = response.data.address.city || response.data.address.city_district;
                    factory.value.address.state = response.data.address.country;
                    factory.value.address.street = `${response.data.address.road} ${response.data.address.house_number}`;
                    factory.value.address.zip = response.data.address.postcode;
                } 
            })
            .catch(error => {
                console.error('Error reverse geocoding:', error);
            });
    };
</script>

<style>
    #map {
        width: 100%;
        height: 420px;
    }
</style>