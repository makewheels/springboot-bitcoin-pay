package com.eg.testbitcoinpay.bitcoin.transaction;

import com.eg.testbitcoinpay.bitcoin.address.BitcoinAddress;
import com.eg.testbitcoinpay.bitcoin.jsonrpc.BitcoinJsonRpcService;
import com.eg.testbitcoinpay.bitcoin.jsonrpc.bean.ListtransactionsResponse;
import com.eg.testbitcoinpay.bitcoin.util.BitcoinUtil;
import com.eg.testbitcoinpay.util.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BitcoinTransactionService {
    @Resource
    private BitcoinTransactionRepository bitcoinTransactionRepository;
    @Resource
    private BitcoinJsonRpcService bitcoinJsonRpcService;

    public void save(BitcoinTransaction bitcoinTransaction) {
        bitcoinTransactionRepository.save(bitcoinTransaction);
    }

    /**
     * 从数据库找到比特币地址对应的交易
     *
     * @param bitcoinAddress
     * @return
     */
    public List<BitcoinTransaction> findBitcoinTransactionByBitcoinAddressFromDatabase(
            BitcoinAddress bitcoinAddress) {
        return bitcoinTransactionRepository.findByReceiveAddress(bitcoinAddress.getAddress());
    }

    /**
     * 从网络找到比特币地址对应的交易
     *
     * @param bitcoinAddress
     * @return
     */
    public List<BitcoinTransaction> findBitcoinTransactionsByBitcoinAddressFromNet(
            BitcoinAddress bitcoinAddress) {
        //在比特币地址表中查到label，然后查询比特币交易
        List<ListtransactionsResponse> listtransactionsResponseList
                = bitcoinJsonRpcService.listtransactions(bitcoinAddress.getLabel());
        //交易列表
        List<BitcoinTransaction> bitcoinTransactionList = new ArrayList<>();
        for (ListtransactionsResponse listtransactionsResponse : listtransactionsResponseList) {
            BitcoinTransaction bitcoinTransaction = new BitcoinTransaction();
            bitcoinTransaction.setCategory(listtransactionsResponse.getCategory());
            bitcoinTransaction.setSatoshi(BitcoinUtil.btcToSatoshi(listtransactionsResponse.getAmount()));
            bitcoinTransaction.setTxid(listtransactionsResponse.getTxid());
            long timereceived = listtransactionsResponse.getTimereceived();
            bitcoinTransaction.setTimereceived(new Date(timereceived * 1000));
            bitcoinTransaction.setConfirmations(listtransactionsResponse.getConfirmations());
            bitcoinTransaction.setReceiveAddress(listtransactionsResponse.getAddress());
            bitcoinTransactionList.add(bitcoinTransaction);
        }
        return bitcoinTransactionList;
    }

    /**
     * 把网络交易信息更新到数据库
     *
     * @param transactionsFromNet
     * @return 数据库是否发生了更新
     */
    public boolean updateTransactionsToDatabase(List<BitcoinTransaction> transactionsFromNet) {
        //更新方法是，根据txid从数据库中找对应交易
        //如果找到，更新确认数
        //如果没找到，那这就是一个新的交易，保存到数据库
        for (BitcoinTransaction bitcoinTransactionFromNet : transactionsFromNet) {
            BitcoinTransaction findBitcoinTransaction
                    = bitcoinTransactionRepository.findByTxid(bitcoinTransactionFromNet.getTxid());
            //如果没有这个交易，保存到数据库
            if (findBitcoinTransaction == null) {
                bitcoinTransactionFromNet.setCreateTime(new Date());
                bitcoinTransactionFromNet.setUuid(UuidUtil.getUuid());
                bitcoinTransactionRepository.save(bitcoinTransactionFromNet);
                return false;
            } else {
                //如果这个交易已经有了，则更新
                //如果确认数发生了变化，则更新
                Integer netConfirmations = bitcoinTransactionFromNet.getConfirmations();
                Integer databaseConfirmations = findBitcoinTransaction.getConfirmations();
                if (!netConfirmations.equals(databaseConfirmations)) {
                    findBitcoinTransaction.setConfirmations(netConfirmations);
                    bitcoinTransactionRepository.save(findBitcoinTransaction);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
