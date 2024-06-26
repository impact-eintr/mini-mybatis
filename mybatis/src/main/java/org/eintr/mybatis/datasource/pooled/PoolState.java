package org.eintr.mybatis.datasource.pooled;

import java.util.ArrayList;
import java.util.List;

public class PoolState {
    protected PooledDataSource dataSource; // 数据源

    protected final List<PooledConnection> idleConnections = new ArrayList<>();
    protected final List<PooledConnection> activeConnections = new ArrayList<>();

    // 请求次数
    protected long requestCount = 0;
    // 总请求时间 accumulate 累积
    protected long accumulatedRequestTime = 0;
    protected long accumulatedCheckoutTime = 0;
    protected long claimedOverdueConnectionCount = 0; // overdue逾期 claimed 声称
    protected long accumulatedCheckoutTimeOfOverdueConnections = 0; // 过期连接累积检查时间
    // 总等待时间
    protected long accumulatedWaitTime = 0;
    // 要等待的次数
    protected long hadToWaitCount = 0;
    // 失败连接次数
    protected long badConnectionCount = 0;

    public synchronized long getAverageRequestTime() {
        return requestCount == 0 ? 0 : accumulatedRequestTime / requestCount;
    }

    public synchronized long getAverageWaitTime() {
        return hadToWaitCount == 0 ? 0 : accumulatedWaitTime / hadToWaitCount;
    }


    public synchronized long getAverageOverdueCheckoutTime() {
        return claimedOverdueConnectionCount == 0 ?
                0 : accumulatedCheckoutTimeOfOverdueConnections / claimedOverdueConnectionCount;
    }

    public synchronized long getClaimedOverdueConnectionCount() {
        return claimedOverdueConnectionCount;
    }

    public synchronized long getAverageCheckoutTime() {
        return requestCount == 0 ? 0 : accumulatedWaitTime / requestCount;
    }

    public synchronized int getIdleConnectionCount() {
        return idleConnections.size();
    }

    public synchronized int getActiveConnectionCount() {
        return activeConnections.size();
    }

    public PoolState(PooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public synchronized long getRequestCount() {
        return requestCount;
    }


    public synchronized long getHadToWaitCount() {
        return hadToWaitCount;
    }

    public synchronized long getBadConnectionCount() {
        return badConnectionCount;
    }

}
