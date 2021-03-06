package cbs.repository.code.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActtrfExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public ActtrfExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAtrcdeIsNull() {
            addCriterion("ATRCDE is null");
            return (Criteria) this;
        }

        public Criteria andAtrcdeIsNotNull() {
            addCriterion("ATRCDE is not null");
            return (Criteria) this;
        }

        public Criteria andAtrcdeEqualTo(String value) {
            addCriterion("ATRCDE =", value, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeNotEqualTo(String value) {
            addCriterion("ATRCDE <>", value, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeGreaterThan(String value) {
            addCriterion("ATRCDE >", value, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeGreaterThanOrEqualTo(String value) {
            addCriterion("ATRCDE >=", value, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeLessThan(String value) {
            addCriterion("ATRCDE <", value, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeLessThanOrEqualTo(String value) {
            addCriterion("ATRCDE <=", value, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeLike(String value) {
            addCriterion("ATRCDE like", value, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeNotLike(String value) {
            addCriterion("ATRCDE not like", value, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeIn(List<String> values) {
            addCriterion("ATRCDE in", values, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeNotIn(List<String> values) {
            addCriterion("ATRCDE not in", values, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeBetween(String value1, String value2) {
            addCriterion("ATRCDE between", value1, value2, "atrcde");
            return (Criteria) this;
        }

        public Criteria andAtrcdeNotBetween(String value1, String value2) {
            addCriterion("ATRCDE not between", value1, value2, "atrcde");
            return (Criteria) this;
        }

        public Criteria andTrfseqIsNull() {
            addCriterion("TRFSEQ is null");
            return (Criteria) this;
        }

        public Criteria andTrfseqIsNotNull() {
            addCriterion("TRFSEQ is not null");
            return (Criteria) this;
        }

        public Criteria andTrfseqEqualTo(Short value) {
            addCriterion("TRFSEQ =", value, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfseqNotEqualTo(Short value) {
            addCriterion("TRFSEQ <>", value, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfseqGreaterThan(Short value) {
            addCriterion("TRFSEQ >", value, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfseqGreaterThanOrEqualTo(Short value) {
            addCriterion("TRFSEQ >=", value, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfseqLessThan(Short value) {
            addCriterion("TRFSEQ <", value, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfseqLessThanOrEqualTo(Short value) {
            addCriterion("TRFSEQ <=", value, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfseqIn(List<Short> values) {
            addCriterion("TRFSEQ in", values, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfseqNotIn(List<Short> values) {
            addCriterion("TRFSEQ not in", values, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfseqBetween(Short value1, Short value2) {
            addCriterion("TRFSEQ between", value1, value2, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfseqNotBetween(Short value1, Short value2) {
            addCriterion("TRFSEQ not between", value1, value2, "trfseq");
            return (Criteria) this;
        }

        public Criteria andTrfkidIsNull() {
            addCriterion("TRFKID is null");
            return (Criteria) this;
        }

        public Criteria andTrfkidIsNotNull() {
            addCriterion("TRFKID is not null");
            return (Criteria) this;
        }

        public Criteria andTrfkidEqualTo(String value) {
            addCriterion("TRFKID =", value, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidNotEqualTo(String value) {
            addCriterion("TRFKID <>", value, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidGreaterThan(String value) {
            addCriterion("TRFKID >", value, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidGreaterThanOrEqualTo(String value) {
            addCriterion("TRFKID >=", value, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidLessThan(String value) {
            addCriterion("TRFKID <", value, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidLessThanOrEqualTo(String value) {
            addCriterion("TRFKID <=", value, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidLike(String value) {
            addCriterion("TRFKID like", value, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidNotLike(String value) {
            addCriterion("TRFKID not like", value, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidIn(List<String> values) {
            addCriterion("TRFKID in", values, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidNotIn(List<String> values) {
            addCriterion("TRFKID not in", values, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidBetween(String value1, String value2) {
            addCriterion("TRFKID between", value1, value2, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfkidNotBetween(String value1, String value2) {
            addCriterion("TRFKID not between", value1, value2, "trfkid");
            return (Criteria) this;
        }

        public Criteria andTrfnumIsNull() {
            addCriterion("TRFNUM is null");
            return (Criteria) this;
        }

        public Criteria andTrfnumIsNotNull() {
            addCriterion("TRFNUM is not null");
            return (Criteria) this;
        }

        public Criteria andTrfnumEqualTo(String value) {
            addCriterion("TRFNUM =", value, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumNotEqualTo(String value) {
            addCriterion("TRFNUM <>", value, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumGreaterThan(String value) {
            addCriterion("TRFNUM >", value, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumGreaterThanOrEqualTo(String value) {
            addCriterion("TRFNUM >=", value, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumLessThan(String value) {
            addCriterion("TRFNUM <", value, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumLessThanOrEqualTo(String value) {
            addCriterion("TRFNUM <=", value, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumLike(String value) {
            addCriterion("TRFNUM like", value, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumNotLike(String value) {
            addCriterion("TRFNUM not like", value, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumIn(List<String> values) {
            addCriterion("TRFNUM in", values, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumNotIn(List<String> values) {
            addCriterion("TRFNUM not in", values, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumBetween(String value1, String value2) {
            addCriterion("TRFNUM between", value1, value2, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfnumNotBetween(String value1, String value2) {
            addCriterion("TRFNUM not between", value1, value2, "trfnum");
            return (Criteria) this;
        }

        public Criteria andTrfoprIsNull() {
            addCriterion("TRFOPR is null");
            return (Criteria) this;
        }

        public Criteria andTrfoprIsNotNull() {
            addCriterion("TRFOPR is not null");
            return (Criteria) this;
        }

        public Criteria andTrfoprEqualTo(Short value) {
            addCriterion("TRFOPR =", value, "trfopr");
            return (Criteria) this;
        }

        public Criteria andTrfoprNotEqualTo(Short value) {
            addCriterion("TRFOPR <>", value, "trfopr");
            return (Criteria) this;
        }

        public Criteria andTrfoprGreaterThan(Short value) {
            addCriterion("TRFOPR >", value, "trfopr");
            return (Criteria) this;
        }

        public Criteria andTrfoprGreaterThanOrEqualTo(Short value) {
            addCriterion("TRFOPR >=", value, "trfopr");
            return (Criteria) this;
        }

        public Criteria andTrfoprLessThan(Short value) {
            addCriterion("TRFOPR <", value, "trfopr");
            return (Criteria) this;
        }

        public Criteria andTrfoprLessThanOrEqualTo(Short value) {
            addCriterion("TRFOPR <=", value, "trfopr");
            return (Criteria) this;
        }

        public Criteria andTrfoprIn(List<Short> values) {
            addCriterion("TRFOPR in", values, "trfopr");
            return (Criteria) this;
        }

        public Criteria andTrfoprNotIn(List<Short> values) {
            addCriterion("TRFOPR not in", values, "trfopr");
            return (Criteria) this;
        }

        public Criteria andTrfoprBetween(Short value1, Short value2) {
            addCriterion("TRFOPR between", value1, value2, "trfopr");
            return (Criteria) this;
        }

        public Criteria andTrfoprNotBetween(Short value1, Short value2) {
            addCriterion("TRFOPR not between", value1, value2, "trfopr");
            return (Criteria) this;
        }

        public Criteria andAmttypIsNull() {
            addCriterion("AMTTYP is null");
            return (Criteria) this;
        }

        public Criteria andAmttypIsNotNull() {
            addCriterion("AMTTYP is not null");
            return (Criteria) this;
        }

        public Criteria andAmttypEqualTo(String value) {
            addCriterion("AMTTYP =", value, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypNotEqualTo(String value) {
            addCriterion("AMTTYP <>", value, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypGreaterThan(String value) {
            addCriterion("AMTTYP >", value, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypGreaterThanOrEqualTo(String value) {
            addCriterion("AMTTYP >=", value, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypLessThan(String value) {
            addCriterion("AMTTYP <", value, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypLessThanOrEqualTo(String value) {
            addCriterion("AMTTYP <=", value, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypLike(String value) {
            addCriterion("AMTTYP like", value, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypNotLike(String value) {
            addCriterion("AMTTYP not like", value, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypIn(List<String> values) {
            addCriterion("AMTTYP in", values, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypNotIn(List<String> values) {
            addCriterion("AMTTYP not in", values, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypBetween(String value1, String value2) {
            addCriterion("AMTTYP between", value1, value2, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmttypNotBetween(String value1, String value2) {
            addCriterion("AMTTYP not between", value1, value2, "amttyp");
            return (Criteria) this;
        }

        public Criteria andAmtsdeIsNull() {
            addCriterion("AMTSDE is null");
            return (Criteria) this;
        }

        public Criteria andAmtsdeIsNotNull() {
            addCriterion("AMTSDE is not null");
            return (Criteria) this;
        }

        public Criteria andAmtsdeEqualTo(String value) {
            addCriterion("AMTSDE =", value, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeNotEqualTo(String value) {
            addCriterion("AMTSDE <>", value, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeGreaterThan(String value) {
            addCriterion("AMTSDE >", value, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeGreaterThanOrEqualTo(String value) {
            addCriterion("AMTSDE >=", value, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeLessThan(String value) {
            addCriterion("AMTSDE <", value, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeLessThanOrEqualTo(String value) {
            addCriterion("AMTSDE <=", value, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeLike(String value) {
            addCriterion("AMTSDE like", value, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeNotLike(String value) {
            addCriterion("AMTSDE not like", value, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeIn(List<String> values) {
            addCriterion("AMTSDE in", values, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeNotIn(List<String> values) {
            addCriterion("AMTSDE not in", values, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeBetween(String value1, String value2) {
            addCriterion("AMTSDE between", value1, value2, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmtsdeNotBetween(String value1, String value2) {
            addCriterion("AMTSDE not between", value1, value2, "amtsde");
            return (Criteria) this;
        }

        public Criteria andAmdtlrIsNull() {
            addCriterion("AMDTLR is null");
            return (Criteria) this;
        }

        public Criteria andAmdtlrIsNotNull() {
            addCriterion("AMDTLR is not null");
            return (Criteria) this;
        }

        public Criteria andAmdtlrEqualTo(String value) {
            addCriterion("AMDTLR =", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrNotEqualTo(String value) {
            addCriterion("AMDTLR <>", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrGreaterThan(String value) {
            addCriterion("AMDTLR >", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrGreaterThanOrEqualTo(String value) {
            addCriterion("AMDTLR >=", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrLessThan(String value) {
            addCriterion("AMDTLR <", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrLessThanOrEqualTo(String value) {
            addCriterion("AMDTLR <=", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrLike(String value) {
            addCriterion("AMDTLR like", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrNotLike(String value) {
            addCriterion("AMDTLR not like", value, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrIn(List<String> values) {
            addCriterion("AMDTLR in", values, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrNotIn(List<String> values) {
            addCriterion("AMDTLR not in", values, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrBetween(String value1, String value2) {
            addCriterion("AMDTLR between", value1, value2, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andAmdtlrNotBetween(String value1, String value2) {
            addCriterion("AMDTLR not between", value1, value2, "amdtlr");
            return (Criteria) this;
        }

        public Criteria andUpddatIsNull() {
            addCriterion("UPDDAT is null");
            return (Criteria) this;
        }

        public Criteria andUpddatIsNotNull() {
            addCriterion("UPDDAT is not null");
            return (Criteria) this;
        }

        public Criteria andUpddatEqualTo(Date value) {
            addCriterion("UPDDAT =", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatNotEqualTo(Date value) {
            addCriterion("UPDDAT <>", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatGreaterThan(Date value) {
            addCriterion("UPDDAT >", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDDAT >=", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatLessThan(Date value) {
            addCriterion("UPDDAT <", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatLessThanOrEqualTo(Date value) {
            addCriterion("UPDDAT <=", value, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatIn(List<Date> values) {
            addCriterion("UPDDAT in", values, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatNotIn(List<Date> values) {
            addCriterion("UPDDAT not in", values, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatBetween(Date value1, Date value2) {
            addCriterion("UPDDAT between", value1, value2, "upddat");
            return (Criteria) this;
        }

        public Criteria andUpddatNotBetween(Date value1, Date value2) {
            addCriterion("UPDDAT not between", value1, value2, "upddat");
            return (Criteria) this;
        }

        public Criteria andRecstsIsNull() {
            addCriterion("RECSTS is null");
            return (Criteria) this;
        }

        public Criteria andRecstsIsNotNull() {
            addCriterion("RECSTS is not null");
            return (Criteria) this;
        }

        public Criteria andRecstsEqualTo(String value) {
            addCriterion("RECSTS =", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotEqualTo(String value) {
            addCriterion("RECSTS <>", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsGreaterThan(String value) {
            addCriterion("RECSTS >", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsGreaterThanOrEqualTo(String value) {
            addCriterion("RECSTS >=", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsLessThan(String value) {
            addCriterion("RECSTS <", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsLessThanOrEqualTo(String value) {
            addCriterion("RECSTS <=", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsLike(String value) {
            addCriterion("RECSTS like", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotLike(String value) {
            addCriterion("RECSTS not like", value, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsIn(List<String> values) {
            addCriterion("RECSTS in", values, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotIn(List<String> values) {
            addCriterion("RECSTS not in", values, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsBetween(String value1, String value2) {
            addCriterion("RECSTS between", value1, value2, "recsts");
            return (Criteria) this;
        }

        public Criteria andRecstsNotBetween(String value1, String value2) {
            addCriterion("RECSTS not between", value1, value2, "recsts");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTTRF
     *
     * @mbggenerated do_not_delete_during_merge Sun Nov 21 21:36:06 CST 2010
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTTRF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}