import { configureStore } from '@reduxjs/toolkit';
import { ipv4NetworkReferenceData_prefixLengths, ipv4NetworkReferenceData_privateCidrBlocks } from '../components/networking/InfraServiceAPI';
import networkingRefDataReducer, { prefixLengths, privateCidrBlocks } from '../components/networking/refDataSlice';


const store = configureStore({
    reducer: {
        ipv4NetworkReferenceData: networkingRefDataReducer
    },
});

export default store;

ipv4NetworkReferenceData_prefixLengths().then((response) => {
    // TODO Handle errors, retry until loaded
    // console.log("-> refDataSlice ipv4NetworkReferenceData_prefixLengths: " + JSON.stringify(response.data));
    store.dispatch(prefixLengths(response.data))
});

ipv4NetworkReferenceData_privateCidrBlocks().then((response) => {
    // TODO Handle errors, retry until loaded
    // console.log("-> refDataSlice ipv4NetworkReferenceData_privateCidrBlocks: " + JSON.stringify(response.data));
    store.dispatch(privateCidrBlocks(response.data))
});