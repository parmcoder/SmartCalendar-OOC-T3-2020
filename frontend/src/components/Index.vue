<template>

  <div class="index">
    <v-app class="theme--dark">
      <v-row class="text-center">
        <v-col cols="12">
          <v-img
                  :src="require('../assets/calendar_5-512.png')"
                  class="my-3"
                  contain
                  height="200"
          />
        </v-col>

        <v-col class="mb-4">
          <h1 class="display-2 font-weight-bold mb-3">
            Welcome to Squishtable!
          </h1>
        </v-col>

        <v-col
                class="mb-5"
                cols="12"
        >
          <v-btn to="/register" class="mr-3" rounded x-large color="orange accent-3" dark> sign-up </v-btn>

          <v-btn to="/login" class="ml-3" rounded x-large color="black" dark> log-in </v-btn>

        </v-col>

        <v-col
                class="mb-5"
                cols="12"
        >
        </v-col>

        <v-col
                class="mb-5"
                cols="12"
        >
        </v-col>
      </v-row>
    </v-app>
  </div>
</template>

<script>
  export default {
    computed: {
      loggedIn() {
        return this.$store.state.auth.status.loggedIn;
      }
    },
    created() {
      if (this.loggedIn) {
        this.$router.push('/calendar');
      }
    }
  };
</script>
