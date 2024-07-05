<template>
    <div class="container-fluid footer py-5 mb-0 bg-light wow bounceInUp" data-wow-delay="0.1s">
        <div class="container">
            <div class="row d-flex align-items-center">
                <div class="col-lg-3 col-md-3 d-flex justify-content-center align-items-center">
                        <img :src="factory.imagePath" class="img-fluid rounded footer-img" alt="">
                </div>
                <div class="col-lg-3 col-md-3 d-flex justify-content-center align-items-center">
                    <div class="footer-item">
                        <h1 class="text-dark">{{ factory.name }}</h1>
                        <div class="footer-item">
                            <div class="d-flex align-items-center">
                            <i v-for="n in filledStars" class="fas fa-star text-primary"></i>
                            <i v-for="n in emptyStars" class="far fa-star text-primary"></i>
                            <h5 class="m-2"> {{ factory.rating.toFixed(2) }}</h5>
                        </div>
                    </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 d-flex justify-content-center align-items-center">
                    <div class="footer-item">
                        <h4 class="mb-4">Find Us</h4>
                        <div class="d-flex flex-column align-items-start">
                            <p><i class="fa fa-map-marker-alt text-primary me-2"></i>{{ factory.location.address.street }}, {{ factory.location.address.city }}, {{ factory.location.address.state }}</p>
                            <p><i class="fa fa-clock text-primary me-2"></i>{{ factory.openTime }} - {{ factory.closeTime }}</p>
                            <p><i v-if="factory.status === 'CLOSED'" class="fas fa-door-closed text-primary me-2"></i><i v-else class="fas fa-door-open text-primary me-2"></i>{{ factory.status }}</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 d-flex justify-content-center align-items-center">
                    <div id="map" class="map"></div>
                </div>    
            </div>
        </div>
    </div>
</template>

<script setup>
    import { defineProps, computed, onMounted } from 'vue';
    import 'ol/ol.css';
    import { Map, View } from 'ol';
    import TileLayer from 'ol/layer/Tile';
    import OSM from 'ol/source/OSM';
    import { fromLonLat } from 'ol/proj';
    import { Feature } from 'ol';
    import { Point } from 'ol/geom';
    import { Style, Icon } from 'ol/style';
    import VectorLayer from 'ol/layer/Vector';
    import VectorSource from 'ol/source/Vector';
    import locationDot from '../img/location-dot-solid.svg';

    const props = defineProps({
        factory: {
            type: Object,
            required: true
        }
    });

    onMounted(() => {
        let coordinates = [props.factory.location.longitude, props.factory.location.latitude]
        // Create a new map instance
        const map = new Map({
            target: 'map',
            layers: [
            new TileLayer({
                source: new OSM(),
            }),
            ],
            view: new View({
            center: fromLonLat(coordinates),
            zoom: 5,
            }),
        });

        // Create a feature for the marker
        const marker = new Feature({
            geometry: new Point(fromLonLat(coordinates)),
        });

        // Define a style for the marker
        marker.setStyle(
            new Style({
                image: new Icon({
                    src: locationDot, // Path to your marker icon image
                    anchor: [0.5, 1],
                    scale: 1.5, // Adjust the scale if necessary
                }),
            })
        );

        const vectorSource = new VectorSource({
            features: [marker],
        });

        const vectorLayer = new VectorLayer({
            source: vectorSource,
        });

        map.addLayer(vectorLayer);
    });

    const filledStars = computed(() => Math.round(props.factory.rating));
    const emptyStars = computed(() => 5 - filledStars.value);
</script>

<style scoped>
    .footer-img{
        height: 250px;
    }

    #map {
        width: 100%;
        height: 250px;
    }
</style>