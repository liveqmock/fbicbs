package cbs.batch.ac.bcb8563.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface BCB8563Mapper {

    @Insert("INSERT INTO ACTVHH( ORGIDT,      TLRNUM,      VCHSET," +
            "  SETSEQ,  ERYDAT,    ORGID2,      PRDCDE," +
            "  CRNYER,      PRDSEQ,  FILLER,ORGID3,  CUSIDT, APCODE," +
            " CURCDE,      TXNAMT,  VALDAT,RVSLBL,  ANACDE, FURINF," +
            " FXRATE,      SECCCY,  SECAMT,CORAPC,  VCHATT, THRREF," +
            " VCHAUT,      VCHANO, DEPNUM,TXNBAK,  ACTBAK, CLRBAK,"+
            " ORGID4,      ERYTYP, ERYTIM,      RECSTS )" +
            " SELECT    ORGIDT,      TLRNUM,      VCHSET,SETSEQ,  ERYDAT,    ORGID2, PRDCDE," +
            " CRNYER,      PRDSEQ,      FXEFLG, ORGID3,      CUSIDT,      APCODE," +
            " CURCDE,      TXNAMT,      VALDAT,  RVSLBL,      ANACDE,      FURINF," +
            " FXRATE,      SECCCY,      SECAMT,  CORAPC,      VCHATT,      THRREF," +
            " VCHAUT,      VCHANO,      DEPNUM, TXNBAK,      ACTBAK,      CLRBAK," +
            " ORGID4,      ERYTYP, ERYTIM,      RECSTS FROM  ACTVTH")
    int insertVhh();

    @Delete("DELETE FROM ACTVTH")
    int deleteVth();

    @Insert("INSERT INTO ACTSTM" +
            "(SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,STMDAT," +
            "STMTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            "   VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            "   THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "   STMPNY,NSTMPG,PAGLIN,RECSTS,FILLER,STMDEP,SECLBA,LASBAL,CRACCM)" +
            "   SELECT SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,STMDAT," +
            "   STMTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            "   VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            "   THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "   STMPNY,NSTMPG,PAGLIN,'N',   FILLER, STMDEP,SECLBA,LASBAL,CRACCM" +
            "   FROM ACTNSM WHERE RECSTS = 'P'")
    int insertStmFromNsm();

    @Insert("INSERT INTO ACTSTM" +
            "(SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,STMDAT," +
            "STMTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            "   VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            "   THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "   STMPNY,NSTMPG,PAGLIN,RECSTS,FILLER,STMDEP,SECLBA,LASBAL,CRACCM)" +
            "   SELECT SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,STMDAT," +
            "   STMTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            "   VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            "   THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "   STMPNY,NSTMPG,PAGLIN,'L',   FILLER, STMDEP,SECLBA,LASBAL,CRACCM" +
            "   FROM ACTLSM WHERE RECSTS = 'P'")
    int insertStmFromLsm();

    @Delete("DELETE FROM ACTNSM WHERE RECSTS = 'P'")
    int deleteNsm();

    @Delete("DELETE FROM ACTLSM WHERE RECSTS = 'P'")
    int deleteLsm();

    @Insert("INSERT INTO ACTLGH" +
            "(SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,LEGDAT," +
            "LEGTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            " VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            " THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "  LEGPNY,NLEGPG,PAGLIN,RECSTS,FILLER, LEGDEP,SECLBA,LASBAL)" +
            " SELECT SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,LEGDAT," +
            " LEGTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            " VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            "  THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "   LEGPNY,NLEGPG,PAGLIN,'C',   FILLER, LEGDEP,SECLBA,LASBAL"+
            " FROM ACTLGC  WHERE RECSTS = 'P'")
    int insertLghFromLgc();

    @Insert("INSERT INTO ACTLGH" +
            "(SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,LEGDAT," +
            "LEGTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            " VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            " THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "  LEGPNY,NLEGPG,PAGLIN,RECSTS,FILLER, LEGDEP,SECLBA,LASBAL)" +
            " SELECT SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,LEGDAT," +
            " LEGTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            " VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            "  THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "   LEGPNY,NLEGPG,PAGLIN,'I',   FILLER, LEGDEP,SECLBA,LASBAL"+
            " FROM ACTLGI  WHERE RECSTS = 'P'")
    int insertLghFromLgi();

    @Insert("INSERT INTO ACTLGH" +
            "(SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,LEGDAT," +
            "LEGTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            " VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            " THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "  LEGPNY,NLEGPG,PAGLIN,RECSTS,FILLER, LEGDEP,SECLBA,LASBAL)" +
            " SELECT SYSIDT,ORGIDT,CUSIDT,APCODE,CURCDE,LEGDAT," +
            " LEGTIM,ORGID3,TLRNUM,VCHSET,SETSEQ,TXNAMT,ACTBAL," +
            " VALDAT,RVSLBL,ORGID2,PRDCDE,CRNYER,PRDSEQ," +
            "  THRREF,FURINF,FXRATE,SECCCY,SECPMT,SECBAL," +
            "   LEGPNY,NLEGPG,PAGLIN,'F',   FILLER, LEGDEP,SECLBA,LASBAL"+
            " FROM ACTLGF  WHERE RECSTS = 'P'")
    int insertLghFromLgf();

    @Delete("DELETE FROM ACTLGC  WHERE RECSTS = 'P'")
    int deleteLgc();

    @Delete("DELETE FROM ACTLGI  WHERE RECSTS = 'P'")
    int deleteLgi();

    @Delete("DELETE FROM ACTLGF  WHERE RECSTS = 'P'")
    int deleteLgf();
}