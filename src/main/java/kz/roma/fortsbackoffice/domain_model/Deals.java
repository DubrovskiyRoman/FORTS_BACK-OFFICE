package kz.roma.fortsbackoffice.domain_model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Component
@Scope("prototype")
@Entity
@Table(indexes = {@Index(name = "deals_rowId_index", columnList = "rowId", unique = true)})
public class Deals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long dealNum; // Номер сделки
    private long orderIdBuy; // Номер заявки покупателя
    private long orderIdSell; // Номер заявки продавца
    private long rowId; // В документации replId
    private Integer dealIsin; // ISIN инструмента
    private Long dealQnt; // Количество инструмента в сделках
    private BigDecimal dealPrice; // Цена сделки
    private Date dealsDate; // Время заключения сделки
    private Byte noSystemDeal; // Признак внесистемной сделки, 1 - сделка внесистемная, 0 - системная (покаупка/продажа)
    private Long buyerDealStatus; // Статус сделки со стороны покупателя
    private Long sellerDealStatus; // Статус сделки со стороны продавца
    private String buyerCode; // Код покупателя
    private String sellerCode; // Код продавца
    private String buyerCompanyCode; // Код РТС фирмы покупателя
    private String sellerCompanyCode; // Код РТС фирмы продавца
    @Temporal(TemporalType.DATE)
    private Date downloadDate; // дата загрузки сделки

    public long getDealNum() {
        return dealNum;
    }

    public void setDealNum(long dealNum) {
        this.dealNum = dealNum;
    }

    public long getOrderIdBuy() {
        return orderIdBuy;
    }

    public void setOrderIdBuy(long orderIdBuy) {
        this.orderIdBuy = orderIdBuy;
    }

    public long getOrderIdSell() {
        return orderIdSell;
    }

    public void setOrderIdSell(long orderIdSell) {
        this.orderIdSell = orderIdSell;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public Integer getDealIsin() {
        return dealIsin;
    }

    public void setDealIsin(Integer dealIsin) {
        this.dealIsin = dealIsin;
    }

    public Long getDealQnt() {
        return dealQnt;
    }

    public void setDealQnt(Long dealQnt) {
        this.dealQnt = dealQnt;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public Date getDealsDate() {
        return dealsDate;
    }

    public void setDealsDate(Date dealsDate) {
        this.dealsDate = dealsDate;
    }

    public Byte getNoSystemDeal() {
        return noSystemDeal;
    }

    public void setNoSystemDeal(Byte noSystemDeal) {
        this.noSystemDeal = noSystemDeal;
    }

    public Long getBuyerDealStatus() {
        return buyerDealStatus;
    }

    public void setBuyerDealStatus(Long buyerDealStatus) {
        this.buyerDealStatus = buyerDealStatus;
    }

    public Long getSellerDealStatus() {
        return sellerDealStatus;
    }

    public void setSellerDealStatus(Long sellerDealStatus) {
        this.sellerDealStatus = sellerDealStatus;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getBuyerCompanyCode() {
        return buyerCompanyCode;
    }

    public void setBuyerCompanyCode(String buyerCompanyCode) {
        this.buyerCompanyCode = buyerCompanyCode;
    }

    public String getSellerCompanyCode() {
        return sellerCompanyCode;
    }

    public void setSellerCompanyCode(String sellerCompanyCode) {
        this.sellerCompanyCode = sellerCompanyCode;
    }

    public Date getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }

    @Override
    public String toString() {
        return "Deals{" +
                "id=" + id +
                ", dealNum=" + dealNum +
                ", orderIdBuy=" + orderIdBuy +
                ", orderIdSell=" + orderIdSell +
                ", rowId=" + rowId +
                ", dealIsin=" + dealIsin +
                ", dealQnt=" + dealQnt +
                ", dealPrice=" + dealPrice +
                ", dealsDate=" + dealsDate +
                ", noSystemDeal=" + noSystemDeal +
                ", buyerDealStatus=" + buyerDealStatus +
                ", sellerDealStatus=" + sellerDealStatus +
                ", buyerCode='" + buyerCode + '\'' +
                ", sellerCode='" + sellerCode + '\'' +
                ", buyerCompanyCode='" + buyerCompanyCode + '\'' +
                ", sellerCompanyCode='" + sellerCompanyCode + '\'' +
                ", downloadDate=" + downloadDate +
                '}';
    }
}
