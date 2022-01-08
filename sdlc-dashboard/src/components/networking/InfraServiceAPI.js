import axios from "axios"

const baseURL = process.env.REACT_APP_infra_service_url;

const privateCidrBlocksURL = baseURL + "/ipv4-network-reference-data/private-cidr-blocks";
const ipv4NetworkReferenceData_privateCidrBlocks = async() => {
    const response = await axios.get(privateCidrBlocksURL, { withCredentials: true });
    return response;
};

const prefixLengthsURL = baseURL + "/ipv4-network-reference-data/prefix-lengths";
const ipv4NetworkReferenceData_prefixLengths = async() => {
    const response = await axios.get(prefixLengthsURL, { withCredentials: true });
    return response;
};

export { ipv4NetworkReferenceData_privateCidrBlocks, ipv4NetworkReferenceData_prefixLengths }