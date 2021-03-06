package cbs.repository.account.maininfo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActbvaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    public ActbvaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
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
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
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

        public Criteria andOrgidtIsNull() {
            addCriterion("ORGIDT is null");
            return (Criteria) this;
        }

        public Criteria andOrgidtIsNotNull() {
            addCriterion("ORGIDT is not null");
            return (Criteria) this;
        }

        public Criteria andOrgidtEqualTo(String value) {
            addCriterion("ORGIDT =", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotEqualTo(String value) {
            addCriterion("ORGIDT <>", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtGreaterThan(String value) {
            addCriterion("ORGIDT >", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtGreaterThanOrEqualTo(String value) {
            addCriterion("ORGIDT >=", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtLessThan(String value) {
            addCriterion("ORGIDT <", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtLessThanOrEqualTo(String value) {
            addCriterion("ORGIDT <=", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtLike(String value) {
            addCriterion("ORGIDT like", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotLike(String value) {
            addCriterion("ORGIDT not like", value, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtIn(List<String> values) {
            addCriterion("ORGIDT in", values, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotIn(List<String> values) {
            addCriterion("ORGIDT not in", values, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtBetween(String value1, String value2) {
            addCriterion("ORGIDT between", value1, value2, "orgidt");
            return (Criteria) this;
        }

        public Criteria andOrgidtNotBetween(String value1, String value2) {
            addCriterion("ORGIDT not between", value1, value2, "orgidt");
            return (Criteria) this;
        }

        public Criteria andCusidtIsNull() {
            addCriterion("CUSIDT is null");
            return (Criteria) this;
        }

        public Criteria andCusidtIsNotNull() {
            addCriterion("CUSIDT is not null");
            return (Criteria) this;
        }

        public Criteria andCusidtEqualTo(String value) {
            addCriterion("CUSIDT =", value, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtNotEqualTo(String value) {
            addCriterion("CUSIDT <>", value, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtGreaterThan(String value) {
            addCriterion("CUSIDT >", value, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtGreaterThanOrEqualTo(String value) {
            addCriterion("CUSIDT >=", value, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtLessThan(String value) {
            addCriterion("CUSIDT <", value, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtLessThanOrEqualTo(String value) {
            addCriterion("CUSIDT <=", value, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtLike(String value) {
            addCriterion("CUSIDT like", value, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtNotLike(String value) {
            addCriterion("CUSIDT not like", value, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtIn(List<String> values) {
            addCriterion("CUSIDT in", values, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtNotIn(List<String> values) {
            addCriterion("CUSIDT not in", values, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtBetween(String value1, String value2) {
            addCriterion("CUSIDT between", value1, value2, "cusidt");
            return (Criteria) this;
        }

        public Criteria andCusidtNotBetween(String value1, String value2) {
            addCriterion("CUSIDT not between", value1, value2, "cusidt");
            return (Criteria) this;
        }

        public Criteria andApcodeIsNull() {
            addCriterion("APCODE is null");
            return (Criteria) this;
        }

        public Criteria andApcodeIsNotNull() {
            addCriterion("APCODE is not null");
            return (Criteria) this;
        }

        public Criteria andApcodeEqualTo(String value) {
            addCriterion("APCODE =", value, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeNotEqualTo(String value) {
            addCriterion("APCODE <>", value, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeGreaterThan(String value) {
            addCriterion("APCODE >", value, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeGreaterThanOrEqualTo(String value) {
            addCriterion("APCODE >=", value, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeLessThan(String value) {
            addCriterion("APCODE <", value, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeLessThanOrEqualTo(String value) {
            addCriterion("APCODE <=", value, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeLike(String value) {
            addCriterion("APCODE like", value, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeNotLike(String value) {
            addCriterion("APCODE not like", value, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeIn(List<String> values) {
            addCriterion("APCODE in", values, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeNotIn(List<String> values) {
            addCriterion("APCODE not in", values, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeBetween(String value1, String value2) {
            addCriterion("APCODE between", value1, value2, "apcode");
            return (Criteria) this;
        }

        public Criteria andApcodeNotBetween(String value1, String value2) {
            addCriterion("APCODE not between", value1, value2, "apcode");
            return (Criteria) this;
        }

        public Criteria andCurcdeIsNull() {
            addCriterion("CURCDE is null");
            return (Criteria) this;
        }

        public Criteria andCurcdeIsNotNull() {
            addCriterion("CURCDE is not null");
            return (Criteria) this;
        }

        public Criteria andCurcdeEqualTo(String value) {
            addCriterion("CURCDE =", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotEqualTo(String value) {
            addCriterion("CURCDE <>", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeGreaterThan(String value) {
            addCriterion("CURCDE >", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeGreaterThanOrEqualTo(String value) {
            addCriterion("CURCDE >=", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeLessThan(String value) {
            addCriterion("CURCDE <", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeLessThanOrEqualTo(String value) {
            addCriterion("CURCDE <=", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeLike(String value) {
            addCriterion("CURCDE like", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotLike(String value) {
            addCriterion("CURCDE not like", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeIn(List<String> values) {
            addCriterion("CURCDE in", values, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotIn(List<String> values) {
            addCriterion("CURCDE not in", values, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeBetween(String value1, String value2) {
            addCriterion("CURCDE between", value1, value2, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotBetween(String value1, String value2) {
            addCriterion("CURCDE not between", value1, value2, "curcde");
            return (Criteria) this;
        }

        public Criteria andErydatIsNull() {
            addCriterion("ERYDAT is null");
            return (Criteria) this;
        }

        public Criteria andErydatIsNotNull() {
            addCriterion("ERYDAT is not null");
            return (Criteria) this;
        }

        public Criteria andErydatEqualTo(Date value) {
            addCriterion("ERYDAT =", value, "erydat");
            return (Criteria) this;
        }

        public Criteria andErydatNotEqualTo(Date value) {
            addCriterion("ERYDAT <>", value, "erydat");
            return (Criteria) this;
        }

        public Criteria andErydatGreaterThan(Date value) {
            addCriterion("ERYDAT >", value, "erydat");
            return (Criteria) this;
        }

        public Criteria andErydatGreaterThanOrEqualTo(Date value) {
            addCriterion("ERYDAT >=", value, "erydat");
            return (Criteria) this;
        }

        public Criteria andErydatLessThan(Date value) {
            addCriterion("ERYDAT <", value, "erydat");
            return (Criteria) this;
        }

        public Criteria andErydatLessThanOrEqualTo(Date value) {
            addCriterion("ERYDAT <=", value, "erydat");
            return (Criteria) this;
        }

        public Criteria andErydatIn(List<Date> values) {
            addCriterion("ERYDAT in", values, "erydat");
            return (Criteria) this;
        }

        public Criteria andErydatNotIn(List<Date> values) {
            addCriterion("ERYDAT not in", values, "erydat");
            return (Criteria) this;
        }

        public Criteria andErydatBetween(Date value1, Date value2) {
            addCriterion("ERYDAT between", value1, value2, "erydat");
            return (Criteria) this;
        }

        public Criteria andErydatNotBetween(Date value1, Date value2) {
            addCriterion("ERYDAT not between", value1, value2, "erydat");
            return (Criteria) this;
        }

        public Criteria andTlrnumIsNull() {
            addCriterion("TLRNUM is null");
            return (Criteria) this;
        }

        public Criteria andTlrnumIsNotNull() {
            addCriterion("TLRNUM is not null");
            return (Criteria) this;
        }

        public Criteria andTlrnumEqualTo(String value) {
            addCriterion("TLRNUM =", value, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumNotEqualTo(String value) {
            addCriterion("TLRNUM <>", value, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumGreaterThan(String value) {
            addCriterion("TLRNUM >", value, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumGreaterThanOrEqualTo(String value) {
            addCriterion("TLRNUM >=", value, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumLessThan(String value) {
            addCriterion("TLRNUM <", value, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumLessThanOrEqualTo(String value) {
            addCriterion("TLRNUM <=", value, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumLike(String value) {
            addCriterion("TLRNUM like", value, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumNotLike(String value) {
            addCriterion("TLRNUM not like", value, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumIn(List<String> values) {
            addCriterion("TLRNUM in", values, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumNotIn(List<String> values) {
            addCriterion("TLRNUM not in", values, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumBetween(String value1, String value2) {
            addCriterion("TLRNUM between", value1, value2, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andTlrnumNotBetween(String value1, String value2) {
            addCriterion("TLRNUM not between", value1, value2, "tlrnum");
            return (Criteria) this;
        }

        public Criteria andVchsetIsNull() {
            addCriterion("VCHSET is null");
            return (Criteria) this;
        }

        public Criteria andVchsetIsNotNull() {
            addCriterion("VCHSET is not null");
            return (Criteria) this;
        }

        public Criteria andVchsetEqualTo(Short value) {
            addCriterion("VCHSET =", value, "vchset");
            return (Criteria) this;
        }

        public Criteria andVchsetNotEqualTo(Short value) {
            addCriterion("VCHSET <>", value, "vchset");
            return (Criteria) this;
        }

        public Criteria andVchsetGreaterThan(Short value) {
            addCriterion("VCHSET >", value, "vchset");
            return (Criteria) this;
        }

        public Criteria andVchsetGreaterThanOrEqualTo(Short value) {
            addCriterion("VCHSET >=", value, "vchset");
            return (Criteria) this;
        }

        public Criteria andVchsetLessThan(Short value) {
            addCriterion("VCHSET <", value, "vchset");
            return (Criteria) this;
        }

        public Criteria andVchsetLessThanOrEqualTo(Short value) {
            addCriterion("VCHSET <=", value, "vchset");
            return (Criteria) this;
        }

        public Criteria andVchsetIn(List<Short> values) {
            addCriterion("VCHSET in", values, "vchset");
            return (Criteria) this;
        }

        public Criteria andVchsetNotIn(List<Short> values) {
            addCriterion("VCHSET not in", values, "vchset");
            return (Criteria) this;
        }

        public Criteria andVchsetBetween(Short value1, Short value2) {
            addCriterion("VCHSET between", value1, value2, "vchset");
            return (Criteria) this;
        }

        public Criteria andVchsetNotBetween(Short value1, Short value2) {
            addCriterion("VCHSET not between", value1, value2, "vchset");
            return (Criteria) this;
        }

        public Criteria andSetseqIsNull() {
            addCriterion("SETSEQ is null");
            return (Criteria) this;
        }

        public Criteria andSetseqIsNotNull() {
            addCriterion("SETSEQ is not null");
            return (Criteria) this;
        }

        public Criteria andSetseqEqualTo(Short value) {
            addCriterion("SETSEQ =", value, "setseq");
            return (Criteria) this;
        }

        public Criteria andSetseqNotEqualTo(Short value) {
            addCriterion("SETSEQ <>", value, "setseq");
            return (Criteria) this;
        }

        public Criteria andSetseqGreaterThan(Short value) {
            addCriterion("SETSEQ >", value, "setseq");
            return (Criteria) this;
        }

        public Criteria andSetseqGreaterThanOrEqualTo(Short value) {
            addCriterion("SETSEQ >=", value, "setseq");
            return (Criteria) this;
        }

        public Criteria andSetseqLessThan(Short value) {
            addCriterion("SETSEQ <", value, "setseq");
            return (Criteria) this;
        }

        public Criteria andSetseqLessThanOrEqualTo(Short value) {
            addCriterion("SETSEQ <=", value, "setseq");
            return (Criteria) this;
        }

        public Criteria andSetseqIn(List<Short> values) {
            addCriterion("SETSEQ in", values, "setseq");
            return (Criteria) this;
        }

        public Criteria andSetseqNotIn(List<Short> values) {
            addCriterion("SETSEQ not in", values, "setseq");
            return (Criteria) this;
        }

        public Criteria andSetseqBetween(Short value1, Short value2) {
            addCriterion("SETSEQ between", value1, value2, "setseq");
            return (Criteria) this;
        }

        public Criteria andSetseqNotBetween(Short value1, Short value2) {
            addCriterion("SETSEQ not between", value1, value2, "setseq");
            return (Criteria) this;
        }

        public Criteria andBvadatIsNull() {
            addCriterion("BVADAT is null");
            return (Criteria) this;
        }

        public Criteria andBvadatIsNotNull() {
            addCriterion("BVADAT is not null");
            return (Criteria) this;
        }

        public Criteria andBvadatEqualTo(Date value) {
            addCriterion("BVADAT =", value, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvadatNotEqualTo(Date value) {
            addCriterion("BVADAT <>", value, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvadatGreaterThan(Date value) {
            addCriterion("BVADAT >", value, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvadatGreaterThanOrEqualTo(Date value) {
            addCriterion("BVADAT >=", value, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvadatLessThan(Date value) {
            addCriterion("BVADAT <", value, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvadatLessThanOrEqualTo(Date value) {
            addCriterion("BVADAT <=", value, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvadatIn(List<Date> values) {
            addCriterion("BVADAT in", values, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvadatNotIn(List<Date> values) {
            addCriterion("BVADAT not in", values, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvadatBetween(Date value1, Date value2) {
            addCriterion("BVADAT between", value1, value2, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvadatNotBetween(Date value1, Date value2) {
            addCriterion("BVADAT not between", value1, value2, "bvadat");
            return (Criteria) this;
        }

        public Criteria andBvaamtIsNull() {
            addCriterion("BVAAMT is null");
            return (Criteria) this;
        }

        public Criteria andBvaamtIsNotNull() {
            addCriterion("BVAAMT is not null");
            return (Criteria) this;
        }

        public Criteria andBvaamtEqualTo(Long value) {
            addCriterion("BVAAMT =", value, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaamtNotEqualTo(Long value) {
            addCriterion("BVAAMT <>", value, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaamtGreaterThan(Long value) {
            addCriterion("BVAAMT >", value, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaamtGreaterThanOrEqualTo(Long value) {
            addCriterion("BVAAMT >=", value, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaamtLessThan(Long value) {
            addCriterion("BVAAMT <", value, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaamtLessThanOrEqualTo(Long value) {
            addCriterion("BVAAMT <=", value, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaamtIn(List<Long> values) {
            addCriterion("BVAAMT in", values, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaamtNotIn(List<Long> values) {
            addCriterion("BVAAMT not in", values, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaamtBetween(Long value1, Long value2) {
            addCriterion("BVAAMT between", value1, value2, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaamtNotBetween(Long value1, Long value2) {
            addCriterion("BVAAMT not between", value1, value2, "bvaamt");
            return (Criteria) this;
        }

        public Criteria andBvaflgIsNull() {
            addCriterion("BVAFLG is null");
            return (Criteria) this;
        }

        public Criteria andBvaflgIsNotNull() {
            addCriterion("BVAFLG is not null");
            return (Criteria) this;
        }

        public Criteria andBvaflgEqualTo(String value) {
            addCriterion("BVAFLG =", value, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgNotEqualTo(String value) {
            addCriterion("BVAFLG <>", value, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgGreaterThan(String value) {
            addCriterion("BVAFLG >", value, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgGreaterThanOrEqualTo(String value) {
            addCriterion("BVAFLG >=", value, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgLessThan(String value) {
            addCriterion("BVAFLG <", value, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgLessThanOrEqualTo(String value) {
            addCriterion("BVAFLG <=", value, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgLike(String value) {
            addCriterion("BVAFLG like", value, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgNotLike(String value) {
            addCriterion("BVAFLG not like", value, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgIn(List<String> values) {
            addCriterion("BVAFLG in", values, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgNotIn(List<String> values) {
            addCriterion("BVAFLG not in", values, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgBetween(String value1, String value2) {
            addCriterion("BVAFLG between", value1, value2, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvaflgNotBetween(String value1, String value2) {
            addCriterion("BVAFLG not between", value1, value2, "bvaflg");
            return (Criteria) this;
        }

        public Criteria andBvastsIsNull() {
            addCriterion("BVASTS is null");
            return (Criteria) this;
        }

        public Criteria andBvastsIsNotNull() {
            addCriterion("BVASTS is not null");
            return (Criteria) this;
        }

        public Criteria andBvastsEqualTo(String value) {
            addCriterion("BVASTS =", value, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsNotEqualTo(String value) {
            addCriterion("BVASTS <>", value, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsGreaterThan(String value) {
            addCriterion("BVASTS >", value, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsGreaterThanOrEqualTo(String value) {
            addCriterion("BVASTS >=", value, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsLessThan(String value) {
            addCriterion("BVASTS <", value, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsLessThanOrEqualTo(String value) {
            addCriterion("BVASTS <=", value, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsLike(String value) {
            addCriterion("BVASTS like", value, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsNotLike(String value) {
            addCriterion("BVASTS not like", value, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsIn(List<String> values) {
            addCriterion("BVASTS in", values, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsNotIn(List<String> values) {
            addCriterion("BVASTS not in", values, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsBetween(String value1, String value2) {
            addCriterion("BVASTS between", value1, value2, "bvasts");
            return (Criteria) this;
        }

        public Criteria andBvastsNotBetween(String value1, String value2) {
            addCriterion("BVASTS not between", value1, value2, "bvasts");
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
     * This class corresponds to the database table ACTBVA
     *
     * @mbggenerated do_not_delete_during_merge Tue Dec 14 16:34:04 CST 2010
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ACTBVA
     *
     * @mbggenerated Tue Dec 14 16:34:04 CST 2010
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