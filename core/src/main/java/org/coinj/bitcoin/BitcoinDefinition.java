/**
 * Copyright 2015 BitTechCenter Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coinj.bitcoin;

import org.bitcoinj.core.*;
import org.bitcoinj.params.TestNet2Params;
import org.bitcoinj.store.WalletProtobufSerializer;
import org.coinj.api.*;
import org.coinj.commons.*;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;
import static org.coinj.commons.Util.impossibleNullCheck;

/**
 * Date: 4/28/15
 * Time: 1:09 AM
 *
 * @author Mikhail Kulikov
 */
public class BitcoinDefinition implements CoinDefinition, Serializable {

    public static final BitcoinDefinition INSTANCE = new BitcoinDefinition();

    private static final long serialVersionUID = 1L;

    public static final String NAME = "bitcoin";
    public static final String SIGNING_NAME = "Bitcoin";
    public static final String TICKER = "BTC";
    public static final String URI_SCHEME = "bitcoin";
    public static final int PROTOCOL_VERSION = 70001;
    public static final boolean CHECKPOINTING_SUPPORT = true;
    public static final int CHECKPOINT_DAYS_BACK = 30;
    public static final int TARGET_TIMESPAN = 14 * 24 * 60 * 60;  // 2 weeks per difficulty cycle, on average.
    public static final int TARGET_SPACING = 10 * 60;  // 10 minutes per block.
    public static final int INTERVAL = TARGET_TIMESPAN / TARGET_SPACING;
    public static final int SUBSIDY_DECREASE_BLOCK_COUNT = 210000;
    public static final int SUBSIDY_DECREASE_BLOCK_COUNT_REGTEST = 150;
    public static final long MAX_COINS = 21000000L;
    public static final BigInteger MAIN_MAX_TARGET = Utils.decodeCompactBits(0x1d00ffffL);
    public static final BigInteger TEST_MAX_TARGET = Utils.decodeCompactBits(0x1d00ffffL);
    static final BigInteger UNITTEST_MAX_TARGET = new BigInteger("00ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
    public static final BigInteger OLD_TEST_MAX_TARGET = Utils.decodeCompactBits(0x1d0fffffL);
    public static final BigInteger REGTEST_MAX_TARGET = new BigInteger("7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
    public static final long REFERENCE_DEFAULT_MIN_TX_FEE = 1000;
    public static final int MIN_NONDUST_OUTPUT = 546;
    public static final int MAX_BLOCK_SIZE = 1000 * 1000;
    public static final Integer PORT = 8333;
    public static final Integer TEST_PORT = 18333;
    public static final Integer REGTEST_PORT = 18444;
    public static final Integer PUBKEY_ADDRESS_HEADER = 0;
    public static final Integer DUMPED_PRIVATE_KEY_HEADER = 128;
    public static final Integer TEST_PUBKEY_ADDRESS_HEADER = 111;
    public static final Integer TEST_DUMPED_PRIVATE_KEY_HEADER = 239;
    public static final Integer P2SH_ADDRESS_HEADER = 5;
    public static final Integer TEST_P2SH_ADDRESS_HEADER = 196;
    public static final Integer SPENDABLE_COINBASE_DEPTH = 100;
    public static final Long MAIN_PACKET_MAGIC = 0xf9beb4d9L;
    public static final Long TEST_PACKET_MAGIC = 0x0b110907L;
    public static final Long OLD_TEST_PACKET_MAGIC = 0xfabfb5daL;
    public static final Long REGTEST_PACKET_MAGIC = 0xfabfb5daL;
    static final int ALLOWED_TIME_DRIFT = 2 * 60 * 60; // Same value as official client.

    /** A services flag that denotes whether the peer has a copy of the block chain or not. */
    public static final int NODE_NETWORK = 1;
    /** A flag that denotes whether the peer supports the getutxos message or not. */
    public static final int NODE_GETUTXOS = 2;
    private static final int GETUTXOS_MIN_PROTOCOL_VERSION = 70003;
    /** The smallest protocol version that supports the pong response (BIP 31). Anything beyond version 60000. */
    public static final int PONG_MIN_PROTOCOL_VERSION = 60001;

    private static final String[] MAIN_DNS_SEEDS = new String[] {
            "seed.bitcoin.sipa.be",        // Pieter Wuille
            "dnsseed.bluematt.me",         // Matt Corallo
            "dnsseed.bitcoin.dashjr.org",  // Luke Dashjr
            "seed.bitcoinstats.com",       // Chris Decker
            "seed.bitnodes.io",            // Addy Yeow
    };
    private static final String[] TEST_DNS_SEEDS = new String[] {
            "testnet-seed.alexykot.me",           // Alex Kotenko
            "testnet-seed.bitcoin.schildbach.de", // Andreas Schildbach
            "testnet-seed.bitcoin.petertodd.org"  // Peter Todd
    };

    public static final String ID_MAINNET = "org.bitcoin.production";
    public static final String ID_TESTNET = "org.bitcoin.test";
    public static final String ID_REGTEST = "org.bitcoin.regtest";
    public static final String ID_UNITTESTNET = "org.bitcoinj.unittest";
    public static final String PAYMENT_PROTOCOL_ID_MAINNET = "main";
    public static final String PAYMENT_PROTOCOL_ID_TESTNET = "test";
    private static final int BLOOM_FILTERING_MIN_PROTOCOL_VERSION = 70000;
    private static final int MIN_BROADCAST_CONNECTIONS = 0;

    private static final String GENESIS_TX_IN_BYTES =
            "04ffff001d0104455468652054696d65732030332f4a616e2f32303039204368616e63656c6c6f72206f6e206272696e6b206f66207365636f6e64206261696c6f757420666f722062616e6b73";
    private static final String GENESIS_TX_OUT_BYTES =
            "04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f";
    private static final int GENESIS_BLOCK_VALUE = 50;
    private static final long GENESIS_BLOCK_DIFFICULTY_TARGET = 0x1d00ffffL;
    private static final long MAIN_GENESIS_BLOCK_TIME = 1231006505L;
    private static final long MAIN_GENESIS_BLOCK_NONCE = 2083236893L;
    private static final String MAIN_GENESIS_HASH = "000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f";
    private static final long TEST_GENESIS_BLOCK_TIME = 1296688602L;
    private static final long TEST_GENESIS_BLOCK_NONCE = 414098458L;
    private static final String TEST_GENESIS_HASH = "000000000933ea01ad0ee984209779baaec3ced90fa3f408719526f8d77f4943";
    private static final long OLD_TEST_GENESIS_BLOCK_DIFFICULTY_TARGET = 0x1d07fff8L;
    private static final long OLD_TEST_GENESIS_BLOCK_TIME = 1296688602L;
    private static final long OLD_TEST_GENESIS_BLOCK_NONCE = 384568319L;
    private static final String OLD_TEST_GENESIS_HASH = "00000007199508e34a9ff81e6ec0c477a4cccff2a4767a8eee39c11db367b008";
    private static final long REGTEST_GENESIS_BLOCK_DIFFICULTY_TARGET = 0x207fFFFFL;
    private static final long REGTEST_GENESIS_BLOCK_TIME = 1296688602L;
    private static final long REGTEST_GENESIS_BLOCK_NONCE = 2L;
    private static final String REGTEST_GENESIS_HASH = "0f9188f13cb7b2c71f2a335e3a4fc328bf5beb436012afca590b1a11466e2206";

    /** A value for difficultyTarget (nBits) that allows half of all possible hash solutions. Used in unit testing. */
    private static final long EASIEST_DIFFICULTY_TARGET = 0x207fFFFFL;

    private static final String MAIN_ALERT_KEY = "04fc9702847840aaf195de8442ebecedf5b095cdbb9bc716bda9110971b28a49e0ead8564ff0db22209e0374782c093bb899692d524e9d6a6956e7c5ecbcd68284";
    private static final String TEST_ALERT_KEY = "04302390343f91cc401d56d68b123028bf52e5fca1939df127f63c6467cdf9c8e2c14b61104cf817d0b780da337893ecc4aaff1309e536162dabbdb45200ca2b0a";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getSignedMessageName() {
        return SIGNING_NAME;
    }

    @Override
    public String getTicker() {
        return TICKER;
    }

    @Override
    public String getUriScheme() {
        return URI_SCHEME;
    }

    @Override
    public int getProtocolVersion() {
        return PROTOCOL_VERSION;
    }

    @Override
    public boolean isCheckpointingSupported() {
        return CHECKPOINTING_SUPPORT;
    }

    @Override
    public int getCheckpointDaysBack() {
        return CHECKPOINT_DAYS_BACK;
    }

    @Override
    public void checkpointsSanityCheck(CheckpointManager checkpointStore, Map checkpoints, StandardNetworkId networkId) {
        checkState(checkpointStore.numCheckpoints() == checkpoints.size());

        if (MAIN_NETWORK_STANDARD.equals(networkId)) {
            StoredBlock test = checkpointStore.getCheckpointBefore(1390500000); // Thu Jan 23 19:00:00 CET 2014
            checkState(test.getHeight() == 280224);
            checkState(test.getHeader().getHashAsString()
                    .equals("00000000000000000b5d59a15f831e1c45cb688a4db6b0a60054d49a9997fa34"));
        } else if (TEST_NETWORK_STANDARD.equals(networkId)) {
            StoredBlock test = checkpointStore.getCheckpointBefore(1390500000); // Thu Jan 23 19:00:00 CET 2014
            checkState(test.getHeight() == 167328);
            checkState(test.getHeader().getHashAsString()
                    .equals("0000000000035ae7d5025c2538067fe7adb1cf5d5d9c31b024137d9090ed13a9"));
        }
    }

    @Override
    public long getEasiestDifficultyTarget() {
        return EASIEST_DIFFICULTY_TARGET;
    }

    @Override
    public int getTargetTimespan(Block block, int height, StandardNetworkId standardNetworkId) {
        return TARGET_TIMESPAN;
    }

    @Override
    public int getTargetSpacing(Block block, int height, StandardNetworkId standardNetworkId) {
        return TARGET_SPACING;
    }

    @Override
    public int getInterval(Block block, int height, StandardNetworkId standardNetworkId) {
        return INTERVAL;
    }

    @Override
    public int getAllowedBlockTimeDrift(StandardNetworkId networkId) {
        return ALLOWED_TIME_DRIFT;
    }

    @Override
    public int getIntervalCheckpoints(Block block, int height, StandardNetworkId standardNetworkId) {
        return INTERVAL;
    }

    @Override
    public long getBlockReward(Block block, Block prevBlock, int prevHeight, StandardNetworkId networkId) {
        return Constants.FIFTY_COINS.shiftRight((prevHeight + 1) / SUBSIDY_DECREASE_BLOCK_COUNT).longValue();
    }

    @Override
    public int getSubsidyDecreaseBlockCount(StandardNetworkId networkId) {
        return (Integer) impossibleNullCheck(networkCheck(SUBSIDY_DECREASE_BLOCK_COUNT,
                SUBSIDY_DECREASE_BLOCK_COUNT, SUBSIDY_DECREASE_BLOCK_COUNT_REGTEST, SUBSIDY_DECREASE_BLOCK_COUNT, networkId));
    }

    @Override
    public int getSpendableDepth(StandardNetworkId networkId) {
        return SPENDABLE_COINBASE_DEPTH;
    }

    @Override
    public long getMaxCoins() {
        return MAX_COINS;
    }

    @Override
    public BigInteger getProofOfWorkLimit(StandardNetworkId networkId) {
        if (networkId.str().equals("unitTest")) {
            return UNITTEST_MAX_TARGET;
        }
        return (BigInteger) networkCheck(MAIN_MAX_TARGET, TEST_MAX_TARGET, REGTEST_MAX_TARGET, OLD_TEST_MAX_TARGET, networkId);
    }

    @Override
    public long getDefaultMinTransactionFee() {
        return REFERENCE_DEFAULT_MIN_TX_FEE;
    }

    @Override
    public long getDustLimit() {
        return MIN_NONDUST_OUTPUT;
    }

    @Override
    public int getMaxBlockSize() {
        return MAX_BLOCK_SIZE;
    }

    @Override
    public int getPort(StandardNetworkId networkId) {
        return (Integer) impossibleNullCheck(networkCheck(PORT, TEST_PORT, REGTEST_PORT, TEST_PORT, networkId));
    }

    @Override
    public int getPubkeyAddressHeader(StandardNetworkId networkId) {
        return (Integer) impossibleNullCheck(networkCheck(PUBKEY_ADDRESS_HEADER, TEST_PUBKEY_ADDRESS_HEADER, TEST_PUBKEY_ADDRESS_HEADER, TEST_PUBKEY_ADDRESS_HEADER, networkId));
    }

    @Override
    public int getDumpedPrivateKeyHeader(StandardNetworkId networkId) {
        return (Integer) impossibleNullCheck(networkCheck(DUMPED_PRIVATE_KEY_HEADER,
                TEST_DUMPED_PRIVATE_KEY_HEADER, TEST_DUMPED_PRIVATE_KEY_HEADER, TEST_DUMPED_PRIVATE_KEY_HEADER, networkId));
    }

    @Override
    public int getP2shAddressHeader(StandardNetworkId networkId) {
        return (Integer) impossibleNullCheck(networkCheck(P2SH_ADDRESS_HEADER, TEST_P2SH_ADDRESS_HEADER, TEST_P2SH_ADDRESS_HEADER, TEST_P2SH_ADDRESS_HEADER, networkId));
    }

    @Override
    public void initCheckpoints(CheckpointsContainer checkpointsContainer) {
        checkpointsContainer.put(91722, "00000000000271a2dc26e7667f8419f2e15416dc6955e5a6c6cdf3f2574dd08e");
        checkpointsContainer.put(91812, "00000000000af0aed4792b1acee3d966af36cf5def14935db8de83d6f9306f2f");
        checkpointsContainer.put(91842, "00000000000a4d0a398161ffc163c503763b1f4360639393e0e4c8e300e0caec");
        checkpointsContainer.put(91880, "00000000000743f190a18c5577a3c2d2a1f610ae9601ac046a38084ccb7cd721");
        checkpointsContainer.put(200000, "000000000000034a7dedef4a161fa058a2d67a173a90155f3a2fe6fc132e0ebf");
    }

    @Override
    public long getPacketMagic(StandardNetworkId networkId) {
        return (Long) impossibleNullCheck(networkCheck(MAIN_PACKET_MAGIC, TEST_PACKET_MAGIC, REGTEST_PACKET_MAGIC, OLD_TEST_PACKET_MAGIC, networkId));
    }

    @Override
    public GenesisBlockInfo getGenesisBlockInfo(StandardNetworkId networkId) {
        final GenesisBlockInfo.GenesisBlockInfoBuilder builder = new GenesisBlockInfo.GenesisBlockInfoBuilder();
        builder.setGenesisTxInBytes(GENESIS_TX_IN_BYTES);
        builder.setGenesisTxOutBytes(GENESIS_TX_OUT_BYTES);
        builder.setGenesisBlockValue(GENESIS_BLOCK_VALUE);

        if (MAIN_NETWORK_STANDARD.equals(networkId)) {
            builder.setGenesisBlockDifficultyTarget(GENESIS_BLOCK_DIFFICULTY_TARGET);
            builder.setGenesisBlockTime(MAIN_GENESIS_BLOCK_TIME);
            builder.setGenesisBlockNonce(MAIN_GENESIS_BLOCK_NONCE);
            builder.setGenesisHash(MAIN_GENESIS_HASH);
        } else if (TEST_NETWORK_STANDARD.equals(networkId)) {
            builder.setGenesisBlockDifficultyTarget(GENESIS_BLOCK_DIFFICULTY_TARGET);
            builder.setGenesisBlockTime(TEST_GENESIS_BLOCK_TIME);
            builder.setGenesisBlockNonce(TEST_GENESIS_BLOCK_NONCE);
            builder.setGenesisHash(TEST_GENESIS_HASH);
        } else if (REG_TEST_STANDARD.equals(networkId)) {
            builder.setGenesisBlockDifficultyTarget(REGTEST_GENESIS_BLOCK_DIFFICULTY_TARGET);
            builder.setGenesisBlockTime(REGTEST_GENESIS_BLOCK_TIME);
            builder.setGenesisBlockNonce(REGTEST_GENESIS_BLOCK_NONCE);
            builder.setGenesisHash(REGTEST_GENESIS_HASH);
        } else if (networkId.str().equals(TestNet2Params.TEST_NET2_NET_ID)) {
            builder.setGenesisBlockDifficultyTarget(OLD_TEST_GENESIS_BLOCK_DIFFICULTY_TARGET);
            builder.setGenesisBlockTime(OLD_TEST_GENESIS_BLOCK_TIME);
            builder.setGenesisBlockNonce(OLD_TEST_GENESIS_BLOCK_NONCE);
            builder.setGenesisHash(OLD_TEST_GENESIS_HASH);
        } else {
            throw new NonStandardNetworkException(networkId.str(), NAME);
        }

        return builder.build();
    }

    @Override
    @Nullable
    public String[] getDnsSeeds(StandardNetworkId networkId) {
        return (String[]) networkCheck(MAIN_DNS_SEEDS, TEST_DNS_SEEDS, null, null, networkId);
    }

    @Override
    public String getAlertKey(StandardNetworkId networkId) {
        return (String) networkCheck(MAIN_ALERT_KEY, TEST_ALERT_KEY, MAIN_ALERT_KEY, MAIN_ALERT_KEY, networkId);
    }

    @Override
    public String getIdMainNet() {
        return ID_MAINNET;
    }

    @Override
    public String getIdTestNet() {
        return ID_TESTNET;
    }

    @Override
    public String getIdRegTest() {
        return ID_REGTEST;
    }

    @Override
    public String getIdUnitTestNet() {
        return ID_UNITTESTNET;
    }

    @Override
    public String getPaymentProtocolId(StandardNetworkId networkId) {
        return (String) networkCheck(PAYMENT_PROTOCOL_ID_MAINNET, PAYMENT_PROTOCOL_ID_TESTNET, null, null, networkId);
    }

    @Override
    public int getMinBroadcastConnections() {
        return MIN_BROADCAST_CONNECTIONS;
    }

    @Override
    public boolean isBitcoinPrivateKeyAllowed() {
        return false;
    }

    @Override
    public int getAllowedPrivateKey() {
        return 0;
    }

    @Override
    public boolean isBloomFilteringSupported(VersionMessage versionInfo) {
        return versionInfo.getClientVersion() >= BLOOM_FILTERING_MIN_PROTOCOL_VERSION;
    }

    @Override
    public boolean hasBlockChain(VersionMessage versionInfo) {
        return (versionInfo.getLocalServices() & NODE_NETWORK) == NODE_NETWORK;
    }

    @Override
    public boolean isGetUTXOsSupported(VersionMessage versionInfo) {
        return versionInfo.getClientVersion() >= GETUTXOS_MIN_PROTOCOL_VERSION &&
                (versionInfo.getLocalServices() & NODE_GETUTXOS) == NODE_GETUTXOS;
    }

    @Override
    public boolean isPingPongSupported(VersionMessage versionInfo) {
        return versionInfo.getClientVersion() >= PONG_MIN_PROTOCOL_VERSION;
    }

    @Nullable
    @Override
    public Integer getNodeBloomConstant() {
        return null;
    }

    @Nullable
    @Override
    public Integer getNodeNetworkConstant() {
        return NODE_NETWORK;
    }

    @Nullable
    @Override
    public Integer getNodeGetUtxosConstant() {
        return NODE_GETUTXOS;
    }

    @Nullable
    @Override
    public Integer getNodePongConstant() {
        return null;
    }

    @Override
    public int getMinBloomProtocolVersion() {
        return BLOOM_FILTERING_MIN_PROTOCOL_VERSION;
    }

    @Override
    public int getMinPongProtocolVersion() {
        return PONG_MIN_PROTOCOL_VERSION;
    }

    @Override
    public BlockHasher createBlockHasher() {
        return new BitcoinBlockHasher();
    }

    @Override
    public BlockExtension createBlockExtension(Block block) {
        return EmptyBlockExtension.INSTANCE;
    }

    @Override
    public TransactionExtension createTransactionExtension(Transaction transaction) {
        return new EmptyTransactionExtension(transaction);
    }

    @Override
    public CoinSerializerExtension createCoinSerializerExtension() {
        return EmptyCoinSerializerExtension.INSTANCE;
    }

    @Override
    public BlockChainExtension createBlockChainExtension(AbstractBlockChain blockChain) {
        return new LinearBlockChainExtension(blockChain, testnetDiffDate);
    }

    @Override
    public PeerExtension createPeerExtension(Peer peer) {
        return EmptyPeerExtension.INSTANCE;
    }

    @Override
    public PeerGroupExtension createPeerGroupExtension(PeerGroup peerGroup) {
        return EmptyPeerGroupExtension.INSTANCE;
    }

    @Override
    public TransactionConfidenceExtension createTransactionConfidenceExtension(TransactionConfidence transactionConfidence) {
        return EmptyTransactionConfidenceExtension.INSTANCE;
    }

    @Override
    public WalletCoinSpecifics createWalletCoinSpecifics(Wallet wallet) {
        return EmptyWalletCoinSpecifics.INSTANCE;
    }

    @Override
    public WalletProtobufSerializerExtension createWalletProtobufSerializerExtension(WalletProtobufSerializer walletProtobufSerializer) {
        return EmptyWalletProtobufSerializerExtension.INSTANCE;
    }

    @Override
    public NetworkExtensionsContainer createNetworkExtensionsContainer(NetworkParameters params) {
        return EmptyNetworkExtensions.INSTANCE;
    }

    @Override
    public NetworkExtensionsContainer createNetworkExtensionsContainer(NetworkParameters params, @Nullable NetworkMode networkMode) {
        return EmptyNetworkExtensions.INSTANCE;
    }

    @Nullable
    @Override
    public String getInventoryTypeByCode(int typeCode) {
        return null;
    }

    @Nullable
    @Override
    public Integer getInventoryTypeOrdinal(String type) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof CoinDefinition && getName().equals(((CoinDefinition) o).getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    private static final long testnetDiffDate = 1329264000000L;

    @Nullable
    private static Object networkCheck(@Nullable Object first, @Nullable Object second, @Nullable Object third, @Nullable Object fourth, StandardNetworkId networkId) {
        if (networkId.str().equals(TestNet2Params.TEST_NET2_NET_ID)) {
            return fourth;
        }
        return Util.networkCheck(first, second, third, networkId, NAME);
    }

}
