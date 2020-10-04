package com.eg.testbitcoinpay.bitcoin.address;

import com.eg.testbitcoinpay.bitcoin.jsonrpc.BitcoinJsonRpcService;
import com.eg.testbitcoinpay.bitcoin.jsonrpc.bean.GetaddressinfoResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Service
public class BitcoinAddressService {
    @Resource
    private BitcoinAddressRepository bitcoinAddressRepository;
    @Resource
    private BitcoinJsonRpcService bitcoinJsonRpcService;

    public void save(BitcoinAddress bitcoinAddress) {
        bitcoinAddressRepository.save(bitcoinAddress);
    }

    /**
     * 创建新的比特币地址
     *
     * @param label
     * @return
     */
    public BitcoinAddress createBitcoinAddress(String label) {
        BitcoinAddress bitcoinAddress = new BitcoinAddress();
        //先创建地址
        String addressString;
        if (StringUtils.isNotEmpty(label)) {
            bitcoinAddress.setLabel(label);
            addressString = bitcoinJsonRpcService.getnewaddress(label);
        } else {
            addressString = bitcoinJsonRpcService.getnewaddress();
        }
        bitcoinAddress.setAddress(addressString);
        bitcoinAddress.setCreateTime(new Date());

        //获取地址信息
        GetaddressinfoResponse getaddressinfoResponse
                = bitcoinJsonRpcService.getaddressinfo(addressString);
        bitcoinAddress.setPublicKey(getaddressinfoResponse.getPubkey());
        //比特币区块中给出的创建时间
        bitcoinAddress.setTimestamp(getaddressinfoResponse.getTimestamp());

        //获取地址私钥
        bitcoinAddress.setPrivateKey(bitcoinJsonRpcService.dumpprivkey(addressString));
        return bitcoinAddress;
    }

    /**
     * 根据订单uuid查询比特币地址
     *
     * @param payOrderId
     * @return
     */
    public BitcoinAddress findBitcoinAddressByPayOrderId(Integer payOrderId) {
        Optional<BitcoinAddress> bitcoinAddressOptional = bitcoinAddressRepository.findById(payOrderId);
        return bitcoinAddressOptional.orElse(null);
    }
}
