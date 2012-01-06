package cbs.repository.code.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActathExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public ActathExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTATH
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
     * This method corresponds to the database table ACTATH
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
     * This method corresponds to the database table ACTATH
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTATH
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
     * This class corresponds to the database table ACTATH
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

        public Criteria andDramntIsNull() {
            addCriterion("DRAMNT is null");
            return (Criteria) this;
        }

        public Criteria andDramntIsNotNull() {
            addCriterion("DRAMNT is not null");
            return (Criteria) this;
        }

        public Criteria andDramntEqualTo(Long value) {
            addCriterion("DRAMNT =", value, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDramntNotEqualTo(Long value) {
            addCriterion("DRAMNT <>", value, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDramntGreaterThan(Long value) {
            addCriterion("DRAMNT >", value, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDramntGreaterThanOrEqualTo(Long value) {
            addCriterion("DRAMNT >=", value, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDramntLessThan(Long value) {
            addCriterion("DRAMNT <", value, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDramntLessThanOrEqualTo(Long value) {
            addCriterion("DRAMNT <=", value, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDramntIn(List<Long> values) {
            addCriterion("DRAMNT in", values, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDramntNotIn(List<Long> values) {
            addCriterion("DRAMNT not in", values, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDramntBetween(Long value1, Long value2) {
            addCriterion("DRAMNT between", value1, value2, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDramntNotBetween(Long value1, Long value2) {
            addCriterion("DRAMNT not between", value1, value2, "dramnt");
            return (Criteria) this;
        }

        public Criteria andDtxamtIsNull() {
            addCriterion("DTXAMT is null");
            return (Criteria) this;
        }

        public Criteria andDtxamtIsNotNull() {
            addCriterion("DTXAMT is not null");
            return (Criteria) this;
        }

        public Criteria andDtxamtEqualTo(Long value) {
            addCriterion("DTXAMT =", value, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andDtxamtNotEqualTo(Long value) {
            addCriterion("DTXAMT <>", value, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andDtxamtGreaterThan(Long value) {
            addCriterion("DTXAMT >", value, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andDtxamtGreaterThanOrEqualTo(Long value) {
            addCriterion("DTXAMT >=", value, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andDtxamtLessThan(Long value) {
            addCriterion("DTXAMT <", value, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andDtxamtLessThanOrEqualTo(Long value) {
            addCriterion("DTXAMT <=", value, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andDtxamtIn(List<Long> values) {
            addCriterion("DTXAMT in", values, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andDtxamtNotIn(List<Long> values) {
            addCriterion("DTXAMT not in", values, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andDtxamtBetween(Long value1, Long value2) {
            addCriterion("DTXAMT between", value1, value2, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andDtxamtNotBetween(Long value1, Long value2) {
            addCriterion("DTXAMT not between", value1, value2, "dtxamt");
            return (Criteria) this;
        }

        public Criteria andCramntIsNull() {
            addCriterion("CRAMNT is null");
            return (Criteria) this;
        }

        public Criteria andCramntIsNotNull() {
            addCriterion("CRAMNT is not null");
            return (Criteria) this;
        }

        public Criteria andCramntEqualTo(Long value) {
            addCriterion("CRAMNT =", value, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCramntNotEqualTo(Long value) {
            addCriterion("CRAMNT <>", value, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCramntGreaterThan(Long value) {
            addCriterion("CRAMNT >", value, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCramntGreaterThanOrEqualTo(Long value) {
            addCriterion("CRAMNT >=", value, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCramntLessThan(Long value) {
            addCriterion("CRAMNT <", value, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCramntLessThanOrEqualTo(Long value) {
            addCriterion("CRAMNT <=", value, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCramntIn(List<Long> values) {
            addCriterion("CRAMNT in", values, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCramntNotIn(List<Long> values) {
            addCriterion("CRAMNT not in", values, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCramntBetween(Long value1, Long value2) {
            addCriterion("CRAMNT between", value1, value2, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCramntNotBetween(Long value1, Long value2) {
            addCriterion("CRAMNT not between", value1, value2, "cramnt");
            return (Criteria) this;
        }

        public Criteria andCtxamtIsNull() {
            addCriterion("CTXAMT is null");
            return (Criteria) this;
        }

        public Criteria andCtxamtIsNotNull() {
            addCriterion("CTXAMT is not null");
            return (Criteria) this;
        }

        public Criteria andCtxamtEqualTo(Long value) {
            addCriterion("CTXAMT =", value, "ctxamt");
            return (Criteria) this;
        }

        public Criteria andCtxamtNotEqualTo(Long value) {
            addCriterion("CTXAMT <>", value, "ctxamt");
            return (Criteria) this;
        }

        public Criteria andCtxamtGreaterThan(Long value) {
            addCriterion("CTXAMT >", value, "ctxamt");
            return (Criteria) this;
        }

        public Criteria andCtxamtGreaterThanOrEqualTo(Long value) {
            addCriterion("CTXAMT >=", value, "ctxamt");
            return (Criteria) this;
        }

        public Criteria andCtxamtLessThan(Long value) {
            addCriterion("CTXAMT <", value, "ctxamt");
            return (Criteria) this;
        }

        public Criteria andCtxamtLessThanOrEqualTo(Long value) {
            addCriterion("CTXAMT <=", value, "ctxamt");
            return (Criteria) this;
        }

        public Criteria andCtxamtIn(List<Long> values) {
            addCriterion("CTXAMT in", values, "ctxamt");
            return (Criteria) this;
        }

        public Criteria andCtxamtNotIn(List<Long> values) {
            addCriterion("CTXAMT not in", values, "ctxamt");
            return (Criteria) this;
        }

        public Criteria andCtxamtBetween(Long value1, Long value2) {
            addCriterion("CTXAMT between", value1, value2, "ctxamt");
            return (Criteria) this;
        }

        public Criteria andCtxamtNotBetween(Long value1, Long value2) {
            addCriterion("CTXAMT not between", value1, value2, "ctxamt");
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
     * This class corresponds to the database table ACTATH
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
     * This class corresponds to the database table ACTATH
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