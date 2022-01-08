import { createSlice } from '@reduxjs/toolkit';

export const slice = createSlice({
  name: 'ipv4NetworkReferenceData',
  initialState: {
    privateCidrBlocks: [],
    prefixLengths: []
  },
  reducers: {
    privateCidrBlocks: (state, action) => {
        state.privateCidrBlocks = action.payload;
    },
    prefixLengths: (state, action) => {
      state.prefixLengths = action.payload;
    },
  },
});

export const { privateCidrBlocks, prefixLengths } = slice.actions;

// The function below is called a selector and allows us to select a value from
// the state. Selectors can also be defined inline where they're used instead of
// in the slice file. For example: `useSelector((state) => state.counter.value)`
export const selectPrivateCidrBlocks = state => state.ipv4NetworkReferenceData.privateCidrBlocks
export const selectPrefixLengths = state => {
    // console.log("state=" + JSON.stringify(state));
    return state.ipv4NetworkReferenceData.prefixLengths;
}    

export default slice.reducer