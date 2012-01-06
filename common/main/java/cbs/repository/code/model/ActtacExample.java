package cbs.repository.code.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActtacExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public ActtacExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAC
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
     * This method corresponds to the database table ACTTAC
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
     * This method corresponds to the database table ACTTAC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ACTTAC
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
     * This class corresponds to the database table ACTTAC
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

        public Criteria andTaxcdeIsNull() {
            addCriterion("TAXCDE is null");
            return (Criteria) this;
        }

        public Criteria andTaxcdeIsNotNull() {
            addCriterion("TAXCDE is not null");
            return (Criteria) this;
        }

        public Criteria andTaxcdeEqualTo(String value) {
            addCriterion("TAXCDE =", value, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeNotEqualTo(String value) {
            addCriterion("TAXCDE <>", value, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeGreaterThan(String value) {
            addCriterion("TAXCDE >", value, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeGreaterThanOrEqualTo(String value) {
            addCriterion("TAXCDE >=", value, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeLessThan(String value) {
            addCriterion("TAXCDE <", value, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeLessThanOrEqualTo(String value) {
            addCriterion("TAXCDE <=", value, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeLike(String value) {
            addCriterion("TAXCDE like", value, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeNotLike(String value) {
            addCriterion("TAXCDE not like", value, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeIn(List<String> values) {
            addCriterion("TAXCDE in", values, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeNotIn(List<String> values) {
            addCriterion("TAXCDE not in", values, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeBetween(String value1, String value2) {
            addCriterion("TAXCDE between", value1, value2, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxcdeNotBetween(String value1, String value2) {
            addCriterion("TAXCDE not between", value1, value2, "taxcde");
            return (Criteria) this;
        }

        public Criteria andTaxdcrIsNull() {
            addCriterion("TAXDCR is null");
            return (Criteria) this;
        }

        public Criteria andTaxdcrIsNotNull() {
            addCriterion("TAXDCR is not null");
            return (Criteria) this;
        }

        public Criteria andTaxdcrEqualTo(String value) {
            addCriterion("TAXDCR =", value, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrNotEqualTo(String value) {
            addCriterion("TAXDCR <>", value, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrGreaterThan(String value) {
            addCriterion("TAXDCR >", value, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrGreaterThanOrEqualTo(String value) {
            addCriterion("TAXDCR >=", value, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrLessThan(String value) {
            addCriterion("TAXDCR <", value, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrLessThanOrEqualTo(String value) {
            addCriterion("TAXDCR <=", value, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrLike(String value) {
            addCriterion("TAXDCR like", value, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrNotLike(String value) {
            addCriterion("TAXDCR not like", value, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrIn(List<String> values) {
            addCriterion("TAXDCR in", values, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrNotIn(List<String> values) {
            addCriterion("TAXDCR not in", values, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrBetween(String value1, String value2) {
            addCriterion("TAXDCR between", value1, value2, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxdcrNotBetween(String value1, String value2) {
            addCriterion("TAXDCR not between", value1, value2, "taxdcr");
            return (Criteria) this;
        }

        public Criteria andTaxfg1IsNull() {
            addCriterion("TAXFG1 is null");
            return (Criteria) this;
        }

        public Criteria andTaxfg1IsNotNull() {
            addCriterion("TAXFG1 is not null");
            return (Criteria) this;
        }

        public Criteria andTaxfg1EqualTo(String value) {
            addCriterion("TAXFG1 =", value, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1NotEqualTo(String value) {
            addCriterion("TAXFG1 <>", value, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1GreaterThan(String value) {
            addCriterion("TAXFG1 >", value, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1GreaterThanOrEqualTo(String value) {
            addCriterion("TAXFG1 >=", value, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1LessThan(String value) {
            addCriterion("TAXFG1 <", value, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1LessThanOrEqualTo(String value) {
            addCriterion("TAXFG1 <=", value, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1Like(String value) {
            addCriterion("TAXFG1 like", value, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1NotLike(String value) {
            addCriterion("TAXFG1 not like", value, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1In(List<String> values) {
            addCriterion("TAXFG1 in", values, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1NotIn(List<String> values) {
            addCriterion("TAXFG1 not in", values, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1Between(String value1, String value2) {
            addCriterion("TAXFG1 between", value1, value2, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg1NotBetween(String value1, String value2) {
            addCriterion("TAXFG1 not between", value1, value2, "taxfg1");
            return (Criteria) this;
        }

        public Criteria andTaxfg2IsNull() {
            addCriterion("TAXFG2 is null");
            return (Criteria) this;
        }

        public Criteria andTaxfg2IsNotNull() {
            addCriterion("TAXFG2 is not null");
            return (Criteria) this;
        }

        public Criteria andTaxfg2EqualTo(String value) {
            addCriterion("TAXFG2 =", value, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2NotEqualTo(String value) {
            addCriterion("TAXFG2 <>", value, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2GreaterThan(String value) {
            addCriterion("TAXFG2 >", value, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2GreaterThanOrEqualTo(String value) {
            addCriterion("TAXFG2 >=", value, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2LessThan(String value) {
            addCriterion("TAXFG2 <", value, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2LessThanOrEqualTo(String value) {
            addCriterion("TAXFG2 <=", value, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2Like(String value) {
            addCriterion("TAXFG2 like", value, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2NotLike(String value) {
            addCriterion("TAXFG2 not like", value, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2In(List<String> values) {
            addCriterion("TAXFG2 in", values, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2NotIn(List<String> values) {
            addCriterion("TAXFG2 not in", values, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2Between(String value1, String value2) {
            addCriterion("TAXFG2 between", value1, value2, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg2NotBetween(String value1, String value2) {
            addCriterion("TAXFG2 not between", value1, value2, "taxfg2");
            return (Criteria) this;
        }

        public Criteria andTaxfg3IsNull() {
            addCriterion("TAXFG3 is null");
            return (Criteria) this;
        }

        public Criteria andTaxfg3IsNotNull() {
            addCriterion("TAXFG3 is not null");
            return (Criteria) this;
        }

        public Criteria andTaxfg3EqualTo(String value) {
            addCriterion("TAXFG3 =", value, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3NotEqualTo(String value) {
            addCriterion("TAXFG3 <>", value, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3GreaterThan(String value) {
            addCriterion("TAXFG3 >", value, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3GreaterThanOrEqualTo(String value) {
            addCriterion("TAXFG3 >=", value, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3LessThan(String value) {
            addCriterion("TAXFG3 <", value, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3LessThanOrEqualTo(String value) {
            addCriterion("TAXFG3 <=", value, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3Like(String value) {
            addCriterion("TAXFG3 like", value, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3NotLike(String value) {
            addCriterion("TAXFG3 not like", value, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3In(List<String> values) {
            addCriterion("TAXFG3 in", values, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3NotIn(List<String> values) {
            addCriterion("TAXFG3 not in", values, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3Between(String value1, String value2) {
            addCriterion("TAXFG3 between", value1, value2, "taxfg3");
            return (Criteria) this;
        }

        public Criteria andTaxfg3NotBetween(String value1, String value2) {
            addCriterion("TAXFG3 not between", value1, value2, "taxfg3");
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
     * This class corresponds to the database table ACTTAC
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
     * This class corresponds to the database table ACTTAC
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