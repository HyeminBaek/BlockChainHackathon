var infuraApiKey = '';

module.exports = {
    accounts: [
        {
            address: '0x23eFf9A9B408EC4D00EC5E101b0f8B0e3af95CBf',    // TODO
            key: '9F833EAB8C0472ED28EB92F80D112063D3492517A6328BF1060A4A59E8368227'        // TODO
        },
    ],
    networks: {
        local: 'http://127.0.0.1:7545',
        ropsten: 'https://ropsten.infura.io/v3/' + infuraApiKey,
        rinkeby: 'https://rinkeby.infura.io/v3/' + infuraApiKey,
        kovan: 'https://kovan.infura.io/v3/' + infuraApiKey,
        mainnet: 'https://mainnet.infura.io/v3/' + infuraApiKey,
        hexlant: 'http://106.10.58.158:3000/v1/rpc',
    },
    deployInfo: {
        targetFile: 'erc20.sol',
        targetContract: 'ERC20',
        name: 'RH-',             // TODO
        symbol: 'BLOOD',           // TODO
        supply: '100',           // TODO
    },
    contract: {
        address: '0x74DbdeF70F7981553A6bfBCd32B20df81BDf114B',
    },
    gas: {
        limit: 2000000,
        price: 30000000000, 
    },
    settings: {
        selectedAccountIndex: 0,
        selectedNetwork: 'hexlant',
    }
};
